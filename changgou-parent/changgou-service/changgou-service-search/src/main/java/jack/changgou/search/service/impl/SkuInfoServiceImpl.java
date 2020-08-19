package jack.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import jack.changgou.goods.feign.SkuFeign;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.search.dao.SkuInfoMapper;
import jack.changgou.search.pojo.SkuInfo;
import jack.changgou.search.service.SkuInfoService;
import jack.changgou.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SkuInfoServiceImpl implements SkuInfoService {

    private static final int PAGE_SIZE = 10;

    // 导入feign
    @Autowired
    SkuFeign skuFeign;

    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public void importSkuInfo() {
        int page = 1;
        int size = 100;
        PageInfo<Sku> pageData = null;


        // 循环查询，每次查询出来100条数据
        do {
            // 调用Feign查询
            Result<PageInfo<Sku>> pageInfoResult = skuFeign.searchSkuPaged(page, size);
            pageData = pageInfoResult.getData();
            List<Sku> skuList = pageData.getList();// 保存查询到的数据

            // 将List<Sku>转换为List<SkuInfo>
            List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(skuList), SkuInfo.class);// 通过json字符串作为第三者，避免了操作值
            skuInfoList.forEach(skuInfo -> {
                Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
                // 实现动态生成一个索引域的效果
                skuInfo.setSpecMap(specMap);
            });

            // 调用Dao实现数据批量导入
            skuInfoMapper.saveAll(skuInfoList);

            page++;
        } while (pageData.isHasNextPage());
    }

    /**
     * 按照关键词对Sku中的name字段进行搜索
     *
     * @param conditionMap
     * @return
     */
    @Override
    public Map<String, Object> searchByKeyword(Map<String, String> conditionMap) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        // 组建基础查询条件，即按关键词查询
        SearchSourceBuilder sourceBuilder = buildBasicQuery(conditionMap);

        // 执行一次分组查询，一次性查询出分类、品牌和规格信息
        Map<String, Object> stringObjectMap = groupQuery(sourceBuilder, conditionMap);
        resultMap.putAll(stringObjectMap);

        // 搜索出分页结果
        List<SkuInfo> skuInfoList = getSkuInfoList(conditionMap, sourceBuilder);
        resultMap.put("data", skuInfoList);

        return resultMap;
    }

    /**
     * 封装基本的查询条件
     *
     * @param conditionMap
     * @return
     */
    private SearchSourceBuilder buildBasicQuery(Map<String, String> conditionMap) {
        String keywords = conditionMap.get("keywords");

        // 搜索条件，利用BoolQueryBuilder进行组合查询
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        // 根据关键字进行搜索
        if (!StringUtils.isEmpty(keywords)) {
            boolBuilder.must(QueryBuilders.matchQuery("name", keywords));
        }

        // 如果已经确认好分类，则需要查询指定分类的数据
        if (conditionMap != null && !StringUtils.isEmpty(conditionMap.get("category"))) {
            boolBuilder.must(QueryBuilders.termQuery("categoryName", conditionMap.get("category")));
        }
        // 如果已经确认好品牌，则需要查询指定品牌的数据
        if (conditionMap != null && !StringUtils.isEmpty(conditionMap.get("brand"))) {
            boolBuilder.must(QueryBuilders.termQuery("brandName", conditionMap.get("brand")));
        }

        // 过滤规格参数
        for (String key : conditionMap.keySet()) {
            // 约定规格参数以spec_开头
            if (key.startsWith("spec_")) {
                String specName = key.substring(5);
                String specValue = conditionMap.get(key);
                boolBuilder.must(QueryBuilders.termQuery("specMap." + specName + ".keyword", specValue));// 需要根据索引库的映射而定
            }
        }

        // 过滤价格，实现价格筛选
        String price = conditionMap.get("price");
        if (!StringUtils.isEmpty(price)) {
            price = price.replace("元", "").replace("以上", "");
            String[] prices = price.split("-");
            if (prices != null && prices.length > 0) {
                if (prices[0] != null) {
                    boolBuilder.must(QueryBuilders.rangeQuery("price").gte(Integer.parseInt(prices[0])));// 下限
                }
                if (prices[1] != null) {
                    boolBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.parseInt(prices[0])));// 上限值
                }
            }
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(boolBuilder);

        // 指定排序域和排序规则
        String sortField = conditionMap.get("sortField");
        String sortRule = conditionMap.get("sortRule");
        if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)) {
            sourceBuilder.sort(SortBuilders.fieldSort(sortField).order(SortOrder.valueOf(sortRule.toUpperCase())));
        }

        return sourceBuilder;
    }

    /**
     * 获取搜索结果的集合数据
     *
     * @param searchSourceBuilder
     * @return
     */
    private List<SkuInfo> getSkuInfoList(Map<String, String> conditionMap, SearchSourceBuilder searchSourceBuilder) throws IOException {

        // 分页
        int pageNum = 0;
        int size = PAGE_SIZE;// 获取记录数，默认10
        try {
            pageNum = Integer.parseInt(conditionMap.get("pageNum"));
        } catch (Exception ex) {
            pageNum = 0;
        }
        searchSourceBuilder.from(pageNum * size).size(size);

        // 高亮搜索
        searchSourceBuilder.trackTotalHits(true);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name")
                .preTags("<font color='red'>")
                .postTags("</font>")
                .fragmentSize(100);// 显示100个字符
        searchSourceBuilder.highlighter(highlightBuilder);

        SearchRequest searchRequest = createSearchRequest(searchSourceBuilder);
        // 执行搜索
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<SkuInfo> skuInfoList = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);
            skuInfoList.add(skuInfo);
            // 获取高亮数据字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields != null && highlightFields.get("name") != null) {
                String name = "";
                Text[] fragments = highlightFields.get("name").getFragments();
                for (int i = 0; i < fragments.length; i++) {
                    name += fragments[i].toString();
                }
                skuInfo.setName(name);
            }
        }
        return skuInfoList;
    }


    /**
     * 综合分组查询，搜索所有的分类名、品牌名和规格名
     *
     * @param searchSourceBuilder
     * @param conditionMap
     * @return
     */
    private Map<String, Object> groupQuery(SearchSourceBuilder searchSourceBuilder, Map<String, String> conditionMap) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        searchSourceBuilder.trackTotalHits(true).from(0).size(10000);// 每次检索只查出前10000条记录

        // 封装分组查询请求
        if (conditionMap != null && StringUtils.isEmpty(conditionMap.get("category"))) {
            searchSourceBuilder.aggregation(AggregationBuilders.terms("categoryGroup").field("categoryName"));
        }
        if (conditionMap != null && StringUtils.isEmpty(conditionMap.get("brand"))) {
            searchSourceBuilder.aggregation(AggregationBuilders.terms("brandGroup").field("brandName"));
        }
        searchSourceBuilder.aggregation(AggregationBuilders.terms("specGroup").field("spec"));// 根据spec域进行查询

        // 创建一个请求
        SearchRequest searchRequest = createSearchRequest(searchSourceBuilder);

        // 执行搜索
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 获取分组
        if (conditionMap != null && StringUtils.isEmpty(conditionMap.get("category"))) {
            List<String> categoryNameList = getGroupList(response, "categoryGroup");
            resultMap.put("categoryNames", categoryNameList);
        }
        if (conditionMap != null && StringUtils.isEmpty(conditionMap.get("brand"))) {
            List<String> brandNameList = getGroupList(response, "brandGroup");
            resultMap.put("brandNames", brandNameList);
        }
        Map<String, Set<String>> specMap = getStringSetMap(response, "specGroup");
        resultMap.put("specList", specMap);

        // 计算总页数和当前页
        int pageNum = 1;
        int size = PAGE_SIZE;// 获取记录数，默认10
        try {
            pageNum = Integer.parseInt(conditionMap.get("pageNum"));
        } catch (Exception ex) {
            pageNum = 1;
        }
        long total = response.getHits().getTotalHits().value;
        long totalPages = total / PAGE_SIZE;
        totalPages = total % PAGE_SIZE == 0 ? totalPages : totalPages + 1;
        resultMap.put("total", total);
        resultMap.put("totalPages", totalPages);
        resultMap.put("currentPage", pageNum);

        return resultMap;
    }

    private List<String> getGroupList(SearchResponse response, String categoryGroup) {
        List<String> names = new LinkedList<>();
        ParsedStringTerms categoryTermsName = response.getAggregations().get(categoryGroup);
        categoryTermsName.getBuckets().forEach(naem -> {
            String key = (String) naem.getKey();
            names.add(key);
        });
        return names;
    }

    /**
     * 将返回的结果保存在Set<String>中
     *
     * @param response
     * @return
     */
    private Map<String, Set<String>> getStringSetMap(SearchResponse response, String groupName) {
        Map<String, Set<String>> specMap = new HashMap<>();
        ParsedStringTerms termsName = response.getAggregations().get(groupName);
        termsName.getBuckets().forEach(naem -> {
            String key = (String) naem.getKey();

            // 将规格数据的JSON字符串转换为Map对象
            Map<String, String> tempMap = JSON.parseObject(key, Map.class);
            for (String name : tempMap.keySet()) {
                if (specMap.get(name) == null) {
                    specMap.put(name, new HashSet<String>());
                }
                // 加入到集合中
                specMap.get(name).add(tempMap.get(name));
            }
        });
        return specMap;
    }

    /**
     * 创建搜索请求
     *
     * @param searchSourceBuilder
     * @return
     */
    private SearchRequest createSearchRequest(SearchSourceBuilder searchSourceBuilder) {
        SearchRequest searchRequest = new SearchRequest("skuinfo");
        searchRequest.types("_doc");
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

}

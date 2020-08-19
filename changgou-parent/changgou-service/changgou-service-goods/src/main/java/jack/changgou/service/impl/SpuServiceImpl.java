package jack.changgou.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.SpuMapper;
import jack.changgou.goods.pojo.*;
import jack.changgou.service.BrandService;
import jack.changgou.service.CategoryService;
import jack.changgou.service.SkuService;
import jack.changgou.service.SpuService;
import jack.changgou.vo.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuMapper spuMapper;

    @Autowired
    SkuService skuService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    IdWorker idWorker;// 用于生成商品ID

    @Override
    public List<Spu> getAllSpu() {
        return spuMapper.selectAll();
    }

    @Override
    public Spu getSpuById(Integer id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addSpu(Spu spu) {
        spuMapper.insertSelective(spu);
    }

    @Override
    public void modefySpu(Spu spu) {
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void deleteSpuById(Integer id) {
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 创建搜索条件
     *
     * @param spu
     * @return
     */
    public Example createCriteria(Spu spu) {

        // 创建条件构造器
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照Spu名查询
        if (!StringUtils.isEmpty(spu.getName())) {
            criteria.andLike("name", "%" + spu.getName() + "%");
        }

        return example;
    }

    @Override
    public List<Spu> searchSpu(Spu spu) {
        return spuMapper.selectByExample(createCriteria(spu));
    }

    @Override
    public PageInfo<Spu> getAllSpuPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Spu> spus = spuMapper.selectAll();
        return new PageInfo<>(spus);
    }

    @Override
    public PageInfo<Spu> searchSpuPaged(Spu spu, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Spu> spus = spuMapper.selectByExample(createCriteria(spu));
        return new PageInfo<>(spus);
    }

    @Override
    public void saveGoods(Goods goods) {
        Spu spu = goods.getSpu();

        // 判断是否为修改操作
        if (spu.getId() != null) {
            skuService.deleteSkuBySpuId(spu.getId());// 删除之前的sku列表
            spuMapper.updateByPrimaryKeySelective(spu);// 修改之前的spu
        } else {
            spu.setId(idWorker.nextId());
            spu.setIsMarketable("0");// 未上架
            spu.setStatus("0");// 未审核
            spu.setIsDelete("0");// 未删除
            spuMapper.insertSelective(goods.getSpu());
        }

        Date date = new Date();
        List<Sku> skuList = goods.getSkuList();
        for (Sku sku : skuList) {
            sku.setId(idWorker.nextId());
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setSpuId(spu.getId());
            // 生成规格名称
            String name = goods.getSpu().getName();
            if (StringUtils.isEmpty(sku.getSpec())) {
                sku.setSpec("{}");// 防止前端传来空指针
            }
            Map<String, String> strings = JSON.parseObject(sku.getSpec(), Map.class);
            for (String str : strings.values()) {
                name += " " + str;
            }
            sku.setName(name);
            // 分类Id
            sku.setCategoryId(goods.getSpu().getCategory3Id());
            // 获取分类名和分类Id
            Integer cid = -1;
            if(goods.getSpu().getCategory3Id() != null){
                cid = goods.getSpu().getCategory3Id();
            }else if(goods.getSpu().getCategory2Id() != null){
                cid = goods.getSpu().getCategory2Id();
            }else{
                cid = goods.getSpu().getCategory1Id();
            }
            Category category = categoryService.getCategoryById(cid);
            sku.setCategoryName(category.getName());
            // 获取品牌名
            Brand brand = brandService.getBrandById(goods.getSpu().getBrandId());
            sku.setBrandName(brand.getName());
            skuService.addSku(sku);
        }
    }

    @Override
    public Goods getGoodsBySpuId(Long spuId) {
        Goods goods = null;

        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (spu != null) {
            Sku sku = new Sku();
            sku.setSpuId(spuId);
            List<Sku> skus = skuService.searchSku(sku);

            goods = new Goods();
            goods.setSpu(spu);
            goods.setSkuList(skus);
        }
        return goods;
    }

    @Override
    public void audit(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断商品是否已经标记为删除
        if (spu.getIsDelete().equals("1")) {
            throw new RuntimeException("该商品已经删除");
        }
        spu.setId(spuId);
        spu.setStatus("1");// 审核：0未审核，1已审核
        spu.setIsMarketable("1");// 上架
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void pull(Long spuId) {

        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断商品是否已经标记为删除
        if (spu.getIsDelete().equals("1")) {
            throw new RuntimeException("该商品已经删除");
        }
        spu.setId(spuId);
        spu.setIsMarketable("0");// 下架
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void push(Long spuId) {

        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断商品是否已经标记为删除
        if (spu.getIsDelete().equals("1")) {
            throw new RuntimeException("该商品已经删除");
        }
        // 判断商品未通过审核
        if (spu.getStatus().equals("0")) {
            throw new RuntimeException("该商品尚未通过审核，不能上架");
        }
        spu.setId(spuId);
        spu.setIsMarketable("1");// 上架
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void batchPush(Long[] spuIds) {
        // 将spuIds中已经审核未删除的商品上架
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", "1");
        criteria.andEqualTo("isDelete", "0");
        criteria.andIn("id", Arrays.asList(spuIds));
        Spu spu = new Spu();
        spu.setIsMarketable("1");
        spuMapper.updateByExampleSelective(spu, example);
    }

    @Override
    public void deleteGoods(Long spuId) {

        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断商品是否已经标记为删除
        if (spu.getIsDelete().equals("1")) {
            throw new RuntimeException("该商品已经删除");
        }
        spu.setId(spuId);
        spu.setIsDelete("1");// 删除
        spuMapper.updateByPrimaryKeySelective(spu);
        // 这里没有更新sku
    }

    @Override
    public void findGoods(Long spuId) {

        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断商品是否已经标记为删除
        if (spu.getIsDelete().equals("0")) {
            throw new RuntimeException("该商品并未删除");
        }
        spu.setId(spuId);
        spu.setIsDelete("0");// 找回
        spuMapper.updateByPrimaryKeySelective(spu);
        // 这里没有更新sku
    }
}

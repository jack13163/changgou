package jack.changgou.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

// 索引库
@Document(indexName = "skuinfo", type = "docs")
public class SkuInfo implements Serializable {
    @Id
    private Long id;//商品id

    /**
     * type: 类型，text支持分词
     * index：添加数据时建立索引
     * analyzer：创建索引分词器
     * store：false，是否存储
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;//SKU名称

    @Field(type = FieldType.Double)
    private Integer price;//价格（分）

    private Integer num;//库存数量

    private String image;//商品图片

    private Date createTime;//创建时间

    private Date updateTime;//更新时间

    private Long spuId;//SPUID

    private Integer categoryId;//类目ID

    // keyword不分词，例如在搜索华为手机时，不希望分词为华为和手机，需要作为整体，否则，可能搜索出华为路由器
    @Field(type = FieldType.Keyword)
    private String categoryName;//类目名称

    // 分类和品牌不需要分词
    @Field(type = FieldType.Keyword)
    private String brandName;//品牌名称

    @Field(type = FieldType.Keyword)
    private String spec;//规格

    private Map<String, Object> specMap;//规格参数

    private String status;//商品状态 1-正常，2-下架，3-删除

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Map<String, Object> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, Object> specMap) {
        this.specMap = specMap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.CategoryBrand;

import java.util.List;

public interface CategoryBrandService {
    /**
     * 查询所有的分类和品牌信息
     * @return
     */
    List<CategoryBrand> getAllCategoryBrand();
    /**
     * 根据分类Id查询品牌Id
     * @return
     */
    List<CategoryBrand> getBrandIdsByCategoryId(Integer brandId);
    /**
     * 根据品牌Id查询分类Id
     * @return
     */
    List<CategoryBrand> getCategoryIdsByBrandId(Integer categoryId);
    /**
     * 添加分类和品牌对应关系
     * @return
     */
    void addCategoryBrand(CategoryBrand categoryBrand);
    /**
     * 修改分类和品牌对应关系
     * @return
     */
    void modefyCategoryBrand(CategoryBrand categoryBrand);
    /**
     * 删除某一品牌下的所有分类
     * @return
     */
    void deleteCategoryBrandByBrandId(Integer brandId);
    /**
     * 删除某一分类下的所有品牌
     * @return
     */
    void deleteCategoryBrandByCategoryId(Integer categoryId);
    /**
     * 删除品牌
     * @return
     */
    void deleteCategoryBrand(CategoryBrand categoryBrand);
    /**
     * 分页查询所有的品牌
     * @return
     */
    PageInfo<CategoryBrand> getAllCategoryBrandPaged(Integer page, Integer size);
}

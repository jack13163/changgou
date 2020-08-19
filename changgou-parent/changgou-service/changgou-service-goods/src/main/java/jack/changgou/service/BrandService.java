package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Brand;

import java.util.List;

public interface BrandService {
    /**
     * 查询所有品牌
     * @return
     */
    List<Brand> getAllBrand();
    /**
     * 根据Id查询品牌
     * @return
     */
    Brand getBrandById(Integer id);
    /**
     * 添加品牌
     * @return
     */
    void addBrand(Brand brand);
    /**
     * 修改品牌
     * @return
     */
    void modefyBrand(Brand brand);
    /**
     * 根据Id删除品牌
     * @return
     */
    void deleteBrandById(Integer id);
    /**
     * 根据条件查询品牌
     * @return
     */
    List<Brand> searchBrand(Brand brand);
    /**
     * 分页查询所有的品牌
     * @return
     */
    PageInfo<Brand> getAllBrandPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的品牌
     * @param brand
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> searchBrandPaged(Brand brand, Integer page, Integer size);
    /**
     * 查询某一分类下的所有品牌
     * @return
     */
    public List<Brand> getBrandsByCategoryId(Integer id);
}

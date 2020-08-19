package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.CategoryBrandMapper;
import jack.changgou.goods.pojo.CategoryBrand;
import jack.changgou.service.CategoryBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    CategoryBrandMapper categoryBrandMapper;

    @Override
    public List<CategoryBrand> getAllCategoryBrand() {
        return categoryBrandMapper.selectAll();
    }

    /**
     * 创建搜索条件
     *
     * @param categoryBrand
     * @return
     */
    public Example createCriteria(CategoryBrand categoryBrand) {

        // 创建条件构造器
        Example example = new Example(CategoryBrand.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照品牌Id查询
        if (categoryBrand.getBrandId() != null) {
            criteria.andEqualTo("brandId", categoryBrand.getBrandId());
        }

        // 按照分类Id查询
        if (categoryBrand.getCategoryId() != null) {
            criteria.andEqualTo("categoryId", categoryBrand.getCategoryId());
        }
        return example;
    }

    @Override
    public List<CategoryBrand> getBrandIdsByCategoryId(Integer categoryId) {
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setCategoryId(categoryId);
        return categoryBrandMapper.selectByExample(createCriteria(categoryBrand));
    }

    @Override
    public List<CategoryBrand> getCategoryIdsByBrandId(Integer brandId) {
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(brandId);
        return categoryBrandMapper.selectByExample(createCriteria(categoryBrand));
    }

    @Override
    public void addCategoryBrand(CategoryBrand categoryBrand) {
        categoryBrandMapper.insertSelective(categoryBrand);
    }

    @Override
    public void modefyCategoryBrand(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateByPrimaryKeySelective(categoryBrand);
    }

    @Override
    public void deleteCategoryBrandByCategoryId(Integer categoryId) {
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setCategoryId(categoryId);
        categoryBrandMapper.deleteByExample(createCriteria(categoryBrand));
    }

    @Override
    public void deleteCategoryBrand(CategoryBrand categoryBrand) {
        categoryBrandMapper.deleteByExample(createCriteria(categoryBrand));
    }

    @Override
    public void deleteCategoryBrandByBrandId(Integer brandId) {
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(brandId);
        categoryBrandMapper.deleteByExample(createCriteria(categoryBrand));
    }

    @Override
    public PageInfo<CategoryBrand> getAllCategoryBrandPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<CategoryBrand> categoryBrands = categoryBrandMapper.selectAll();
        return new PageInfo<>(categoryBrands);
    }
}

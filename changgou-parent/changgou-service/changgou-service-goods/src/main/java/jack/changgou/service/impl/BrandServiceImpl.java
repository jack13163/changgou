package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.BrandMapper;
import jack.changgou.goods.pojo.Brand;
import jack.changgou.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    public List<Brand> getAllBrand() {
        return brandMapper.selectAll();
    }

    public Brand getBrandById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void modefyBrand(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void deleteBrandById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 按照品牌名或者首字母查询
     * @param brand
     * @return
     */
    @Override
    public List<Brand> searchBrand(Brand brand) {
        List<Brand> brands = null;
        if(brand != null) {
            // 创建条件构造器
            Example example = createCriteria(brand);
            brands = brandMapper.selectByExample(example);
        }
        return brands;
    }

    /**
     * 创建搜索条件
     * @param brand
     * @return
     */
    public Example createCriteria(Brand brand){

        // 创建条件构造器
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照品牌名查询
        if(!StringUtils.isEmpty(brand.getName())){
            criteria.andLike("name", "%" + brand.getName() + "%");// Brand属性名,搜索条件
        }

        // 按照首字母搜索
        if(!StringUtils.isEmpty(brand.getLetter())){
            criteria.andEqualTo("letter", brand.getLetter());// Brand属性名,搜索条件
        }
        return example;
    }

    /**
     * 查询某一分类下的所有品牌
     * @return
     */
    public List<Brand> getBrandsByCategoryId(Integer id){
        List<Brand> brands = brandMapper.getBrandsByByCategoryId(id);
        return brands;
    }

    /**
     * 分页查询所有数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Brand> getAllBrandPaged(Integer page, Integer size) {
        /**
         * 分页操作：
         *  当前页
         *  每页大小
         */
        PageHelper.startPage(page, size);
        // 查询所有的品牌
        List<Brand> brands = brandMapper.selectAll();
        return new PageInfo<>(brands);
    }

    /**
     * 按照品牌名或者首字母条件分页查询
     * @param brand
     * @return
     */
    @Override
    public PageInfo<Brand> searchBrandPaged(Brand brand, Integer page, Integer size) {
        /**
         * 分页操作：
         *  当前页
         *  每页大小
         */
        PageHelper.startPage(page, size);
        // 创建条件构造器
        Example example = createCriteria(brand);
        // 按照条件查询
        List<Brand> brands = brandMapper.selectByExample(example);
        return new PageInfo<>(brands);
    }
}

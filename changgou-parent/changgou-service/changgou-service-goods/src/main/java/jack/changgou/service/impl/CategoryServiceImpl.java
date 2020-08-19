package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.CategoryMapper;
import jack.changgou.goods.pojo.Category;
import jack.changgou.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.selectAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Override
    public void modefyCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据Id查询某一分类的所有子类
     * @return
     */
    public List<Category> getChildCategoryByParentCategoryId(Integer pid){
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categories = categoryMapper.selectByExample(createCriteria(category));
        return categories;
    }

    /**
     * 创建搜索条件
     * @param category
     * @return
     */
    public Example createCriteria(Category category){

        // 创建条件构造器
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照分类名称查询
        if(!StringUtils.isEmpty(category.getName())){
            criteria.andEqualTo("name", "%" + category.getName() + "%");
        }

        // 按照是否显示查询
        if(!StringUtils.isEmpty(category.getIsShow())){
            criteria.andEqualTo("isShow", "%" + category.getIsShow() + "%");
        }

        // 按照是否导航查询
        if(!StringUtils.isEmpty(category.getIsMenu())){
            criteria.andEqualTo("isMenu", "%" + category.getIsMenu() + "%");
        }

        // 按照上一级Id查询
        if(category.getParentId() != null){
            criteria.andEqualTo("parentId", category.getParentId());
        }

        // 按照模板Id查询
        if(category.getTemplateId() != null){
            criteria.andEqualTo("templateId", category.getTemplateId());
        }
        return example;
    }

    @Override
    public List<Category> searchCategory(Category category) {
        return categoryMapper.selectByExample(createCriteria(category));
    }

    @Override
    public PageInfo<Category> getAllCategoryPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Category> categories = categoryMapper.selectAll();
        return new PageInfo<>(categories);
    }

    @Override
    public PageInfo<Category> searchCategoryPaged(Category category, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Category> categories = categoryMapper.selectByExample(createCriteria(category));
        return new PageInfo<>(categories);
    }
}

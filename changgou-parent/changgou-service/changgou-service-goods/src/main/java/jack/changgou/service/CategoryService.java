package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有分类
     * @return
     */
    List<Category> getAllCategory();
    /**
     * 根据Id查询分类
     * @return
     */
    Category getCategoryById(Integer id);
    /**
     * 添加分类
     * @return
     */
    void addCategory(Category category);
    /**
     * 修改分类
     * @return
     */
    void modefyCategory(Category category);
    /**
     * 根据Id删除分类
     * @return
     */
    void deleteCategoryById(Integer id);
    /**
     * 根据条件查询分类
     * @return
     */
    List<Category> searchCategory(Category category);
    /**
     * 根据Id查询某一分类的所有子类
     * @return
     */
    public List<Category> getChildCategoryByParentCategoryId(Integer pid);
    /**
     * 分页查询所有的分类
     * @return
     */
    PageInfo<Category> getAllCategoryPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的分类
     * @param category
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> searchCategoryPaged(Category category, Integer page, Integer size);
}

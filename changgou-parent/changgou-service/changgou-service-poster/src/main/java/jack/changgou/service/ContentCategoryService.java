package jack.changgou.service;


import com.github.pagehelper.PageInfo;
import jack.changgou.poster.pojo.ContentCategory;

import java.util.List;

public interface ContentCategoryService  {
    /**
     * 查询所有广告内容
     * @return
     */
    List<ContentCategory> getAllContentCategory();
    /**
     * 根据Id查询广告内容
     * @return
     */
    ContentCategory getContentCategoryById(Integer id);
    /**
     * 添加广告内容
     * @return
     */
    void addContentCategory(ContentCategory contentCategory);
    /**
     * 修改品牌
     * @return
     */
    void modefyContentCategory(ContentCategory contentCategory);
    /**
     * 根据Id删除广告内容
     * @return
     */
    void deleteContentCategoryById(Integer id);
    /**
     * 根据条件查询广告内容
     * @return
     */
    List<ContentCategory> searchContentCategory(ContentCategory contentCategory);
    /**
     * 分页查询所有的广告内容
     * @return
     */
    PageInfo<ContentCategory> getAllContentCategoryPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的广告
     * @param contentCategory
     * @param page
     * @param size
     * @return
     */
    PageInfo<ContentCategory> searchContentCategoryPaged(ContentCategory contentCategory, Integer page, Integer size);

}

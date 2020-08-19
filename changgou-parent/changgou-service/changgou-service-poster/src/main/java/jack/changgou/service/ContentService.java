package jack.changgou.service;


import com.github.pagehelper.PageInfo;
import jack.changgou.poster.pojo.Content;

import java.util.List;

public interface ContentService {
    /**
     * 查询所有广告分类信息
     * @return
     */
    List<Content> getAllContent();
    /**
     * 根据Id查询广告分类信息
     * @return
     */
    Content getContentById(Integer id);
    /**
     * 添加广告分类信息
     * @return
     */
    void addContent(Content content);
    /**
     * 修改品牌
     * @return
     */
    void modefyContent(Content content);
    /**
     * 根据Id删除广告分类信息
     * @return
     */
    void deleteContentById(Integer id);
    /**
     * 根据条件查询广告分类信息
     * @return
     */
    List<Content> searchContent(Content content);
    /**
     * 分页查询所有的广告分类信息
     * @return
     */
    PageInfo<Content> getAllContentPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的广告分类信息
     * @param album
     * @param page
     * @param size
     * @return
     */
    PageInfo<Content> searchContentPaged(Content content, Integer page, Integer size);
}

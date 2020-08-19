package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.ContentMapper;
import jack.changgou.poster.pojo.Content;
import jack.changgou.service.ContentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    ContentMapper contentMapper;

    @Override
    public List<Content> getAllContent() {
        return contentMapper.selectAll();
    }

    @Override
    public Content getContentById(Integer id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addContent(Content content) {
        contentMapper.insertSelective(content);
    }

    @Override
    public void modefyContent(Content content) {
        contentMapper.updateByPrimaryKeySelective(content);
    }

    @Override
    public void deleteContentById(Integer id) {
        contentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 创建搜索条件
     *
     * @return
     */
    public Example createExample(Content content) {
        Example example = new Example(Content.class);
        Example.Criteria criteria = example.createCriteria();
        if (content.getCategoryId() != null) {
            criteria.andEqualTo("id", content.getId());
        }
        if (!StringUtils.isEmpty(content.getTitle())) {
            criteria.andLike("title", content.getTitle());
        }
        if (!StringUtils.isEmpty(content.getStatus())) {
            criteria.andEqualTo("status", content.getStatus());
        }
        return example;
    }

    @Override
    public List<Content> searchContent(Content content) {
        return contentMapper.selectByExample(createExample(content));
    }

    @Override
    public PageInfo<Content> getAllContentPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<Content> pageInfo = new PageInfo<>(contentMapper.selectAll());
        return pageInfo;
    }

    @Override
    public PageInfo<Content> searchContentPaged(Content content, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<Content> pageInfo = new PageInfo<>(contentMapper.selectByExample(createExample(content)));
        return pageInfo;
    }
}

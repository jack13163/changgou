package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.ContentCategoryMapper;
import jack.changgou.poster.pojo.ContentCategory;
import jack.changgou.service.ContentCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    ContentCategoryMapper contentCategoryMapper;

    @Override
    public List<ContentCategory> getAllContentCategory() {
        return contentCategoryMapper.selectAll();
    }

    @Override
    public ContentCategory getContentCategoryById(Integer id) {
        return contentCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addContentCategory(ContentCategory contentCategory) {
        contentCategoryMapper.insertSelective(contentCategory);
    }

    @Override
    public void modefyContentCategory(ContentCategory contentCategory) {
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
    }

    @Override
    public void deleteContentCategoryById(Integer id) {
        contentCategoryMapper.deleteByPrimaryKey(id);
    }

    public Example createExample(ContentCategory contentCategory) {
        Example example = new Example(ContentCategory.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isEmpty(contentCategory.getName())) {
            criteria.andLike("name", contentCategory.getName());
        }
        return example;
    }

    @Override
    public List<ContentCategory> searchContentCategory(ContentCategory contentCategory) {
        return contentCategoryMapper.selectByExample(createExample(contentCategory));
    }

    @Override
    public PageInfo<ContentCategory> getAllContentCategoryPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<ContentCategory> pageInfo = new PageInfo<>(contentCategoryMapper.selectAll());
        return pageInfo;
    }

    @Override
    public PageInfo<ContentCategory> searchContentCategoryPaged(ContentCategory contentCategory, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<ContentCategory> pageInfo = new PageInfo<>(contentCategoryMapper.selectByExample(createExample(contentCategory)));
        return pageInfo;
    }
}

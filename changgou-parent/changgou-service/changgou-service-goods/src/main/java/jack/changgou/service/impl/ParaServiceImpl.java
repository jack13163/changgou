package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.ParaMapper;
import jack.changgou.dao.CategoryMapper;
import jack.changgou.goods.pojo.Category;
import jack.changgou.goods.pojo.Para;
import jack.changgou.service.ParaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ParaServiceImpl implements ParaService {

    @Autowired
    ParaMapper paraMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Para> getAllPara() {
        return paraMapper.selectAll();
    }

    @Override
    public Para getParaById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addPara(Para para) {
        paraMapper.insertSelective(para);
    }

    @Override
    public void modefyPara(Para para) {
        paraMapper.updateByPrimaryKey(para);
    }

    @Override
    public void deleteParaById(Integer id) {
        paraMapper.deleteByPrimaryKey(id);
    }

    /**
     * 创建搜索条件
     * @param para
     * @return
     */
    public Example createCriteria(Para para){

        // 创建条件构造器
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照参数名查询
        if(!StringUtils.isEmpty(para.getName())){
            criteria.andEqualTo("name", para.getName());
        }

        // 按照模板Id查询
        if(para.getTemplateId() != null){
            criteria.andEqualTo("templateId", para.getTemplateId());
        }
        return example;
    }

    @Override
    public List<Para> searchPara(Para para) {
        return paraMapper.select(para);
    }

    @Override
    public PageInfo<Para> getAllParaPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Para> paras = paraMapper.selectAll();
        return new PageInfo<>(paras);
    }

    @Override
    public PageInfo<Para> searchParaPaged(Para para, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Para> paras = paraMapper.selectByExample(createCriteria(para));
        return new PageInfo<>(paras);
    }

    @Override
    public List<Para> getParasByCategoryId(Integer id) {
        // 先查询分类表获取模板Id
        Category category = categoryMapper.selectByPrimaryKey(id);
        // 再查询参数表获取模板Id对应的所有参数
        Para para = new Para();
        para.setTemplateId(category.getTemplateId());
        List<Para> paras = paraMapper.select(para);
        return paras;
    }
}

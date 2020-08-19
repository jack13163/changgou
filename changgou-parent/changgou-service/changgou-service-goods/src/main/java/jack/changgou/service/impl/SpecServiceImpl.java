package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.SpecMapper;
import jack.changgou.goods.pojo.Spec;
import jack.changgou.service.SpecService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    SpecMapper specMapper;

    @Override
    public List<Spec> getAllSpec() {
        return specMapper.selectAll();
    }

    @Override
    public Spec getSpecById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addSpec(Spec spec) {
        specMapper.insertSelective(spec);
    }

    @Override
    public void modefySpec(Spec spec) {
        specMapper.updateByPrimaryKeySelective(spec);
    }

    @Override
    public void deleteSpecById(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }

    /**
     * 创建搜索条件
     * @param spec
     * @return
     */
    public Example createCriteria(Spec spec){

        // 创建条件构造器
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照规格名查询
        if(!StringUtils.isEmpty(spec.getName())){
            criteria.andEqualTo("name", spec.getName());
        }

        // 按照模板Id查询
        if(spec.getTemplateId() != null){
            criteria.andEqualTo("templateId", spec.getTemplateId());
        }
        return example;
    }

    @Override
    public List<Spec> searchSpec(Spec spec) {
        return specMapper.select(spec);
    }

    @Override
    public PageInfo<Spec> getAllSpecPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Spec> specs = specMapper.selectAll();
        return new PageInfo<>(specs);
    }

    @Override
    public PageInfo<Spec> searchSpecPaged(Spec spec, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Spec> specs = specMapper.selectByExample(createCriteria(spec));
        return new PageInfo<>(specs);
    }

    @Override
    public List<Spec> getSpecByCategoryId(Integer cid) {
        return specMapper.getSpecByCategoryId(cid);
    }
}

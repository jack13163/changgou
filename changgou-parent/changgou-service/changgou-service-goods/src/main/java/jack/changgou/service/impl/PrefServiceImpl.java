package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.PrefMapper;
import jack.changgou.goods.pojo.Pref;
import jack.changgou.goods.pojo.Brand;
import jack.changgou.service.PrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PrefServiceImpl implements PrefService {
    @Autowired
    PrefMapper PrefMapper;

    @Override
    public List<Pref> getAllPref() {
        return PrefMapper.selectAll();
    }

    @Override
    public Pref getPrefById(Integer id) {
        return PrefMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addPref(Pref Pref) {
        PrefMapper.insertSelective(Pref);
    }

    @Override
    public void modefyPref(Pref Pref) {
        PrefMapper.updateByPrimaryKeySelective(Pref);
    }

    @Override
    public void deletePrefById(Integer id) {
        PrefMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Pref> searchPref(Pref Pref) {
        return PrefMapper.selectByExample(createCriteria(Pref));
    }


    /**
     * 创建搜索条件
     *
     * @param Pref
     * @return
     */
    public Example createCriteria(Pref Pref) {

        // 创建条件构造器
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照相册名模糊查询
        if (null != Pref.getCateId()) {
            criteria.andEqualTo("cateId", Pref.getCateId());// 搜索条件
        }
        return example;
    }

    @Override
    public PageInfo<Pref> getAllPrefPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Pref> Prefs = PrefMapper.selectAll();
        return new PageInfo<>(Prefs);
    }

    @Override
    public PageInfo<Pref> searchPrefPaged(Pref Pref, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Pref> Prefs = PrefMapper.selectByExample(createCriteria(Pref));
        return new PageInfo<>(Prefs);
    }
}

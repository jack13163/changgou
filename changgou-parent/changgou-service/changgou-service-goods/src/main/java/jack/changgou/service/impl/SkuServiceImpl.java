package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Spec;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.service.SkuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    jack.changgou.dao.SkuMapper SkuMapper;

    @Override
    public List<Sku> getAllSku() {
        return SkuMapper.selectAll();
    }

    @Override
    public Sku getSkuById(Long id) {
        return SkuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addSku(Sku Sku) {
        SkuMapper.insertSelective(Sku);
    }

    @Override
    public void modefySku(Sku Sku) {
        SkuMapper.updateByPrimaryKeySelective(Sku);
    }

    @Override
    public void deleteSkuById(Long id) {
        SkuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteSkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        SkuMapper.delete(sku);
    }

    /**
     * 创建搜索条件
     * @param Sku
     * @return
     */
    public Example createCriteria(Sku Sku){

        // 创建条件构造器
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照Sku名查询
        if(!StringUtils.isEmpty(Sku.getName())){
            criteria.andLike("name", "%" + Sku.getName() + "%");
        }

        return example;
    }

    @Override
    public List<Sku> searchSku(Sku Sku) {
        return SkuMapper.select(Sku);
    }

    @Override
    public PageInfo<Sku> getAllSkuPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Sku> Skus = SkuMapper.selectAll();
        return new PageInfo<>(Skus);
    }

    @Override
    public PageInfo<Sku> searchSkuPaged(Sku Sku, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Sku> Skus = SkuMapper.selectByExample(createCriteria(Sku));
        return new PageInfo<>(Skus);
    }
}

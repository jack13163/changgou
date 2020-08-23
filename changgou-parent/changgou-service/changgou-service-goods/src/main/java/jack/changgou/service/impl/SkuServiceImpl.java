package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.SkuMapper;
import jack.changgou.goods.pojo.Spec;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.service.SkuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuMapper skuMapper;

    @Override
    public List<Sku> getAllSku() {
        return skuMapper.selectAll();
    }

    @Override
    public Sku getSkuById(Long id) {
        return skuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addSku(Sku Sku) {
        skuMapper.insertSelective(Sku);
    }

    @Override
    public void modefySku(Sku Sku) {
        skuMapper.updateByPrimaryKeySelective(Sku);
    }

    @Override
    public void deleteSkuById(Long id) {
        skuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteSkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        skuMapper.delete(sku);
    }

    /**
     * 创建搜索条件
     *
     * @param Sku
     * @return
     */
    public Example createCriteria(Sku Sku) {

        // 创建条件构造器
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照Sku名查询
        if (!StringUtils.isEmpty(Sku.getName())) {
            criteria.andLike("name", "%" + Sku.getName() + "%");
        }

        return example;
    }

    @Override
    public List<Sku> searchSku(Sku Sku) {
        return skuMapper.select(Sku);
    }

    @Override
    public PageInfo<Sku> getAllSkuPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Sku> Skus = skuMapper.selectAll();
        return new PageInfo<>(Skus);
    }

    @Override
    public PageInfo<Sku> searchSkuPaged(Sku Sku, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Sku> Skus = skuMapper.selectByExample(createCriteria(Sku));
        return new PageInfo<>(Skus);
    }

    /**
     * 商品批量减少库存
     *
     * @param map
     */
    @Override
    public void decrCount(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            Long skuId = Long.parseLong(key);
            Object obj = map.get(key);
            Integer count = Integer.parseInt(obj.toString());
            // Integer count = Integer.valueOf(.toString());

            // 行级锁防止库存中商品超卖
            // update tb_sku set num=num-#{num} where id=#{id} and num>=#{num}
            // 数据库中每条记录都拥有行级锁，任意时刻只能允许一个事务修改改行记录
            // 只有等事务结束后，其他事务才能访问

            int columns = skuMapper.decrCount(skuId, count);
            if(columns <= 0){
                throw new RuntimeException("减少库存失败");
            }
        }
    }
}

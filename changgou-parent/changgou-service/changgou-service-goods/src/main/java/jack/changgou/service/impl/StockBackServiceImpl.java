package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.StockBackMapper;
import jack.changgou.goods.pojo.StockBack;
import jack.changgou.service.StockBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class StockBackServiceImpl implements StockBackService {

    @Autowired
    StockBackMapper stockBackMapper;

    @Override
    public List<StockBack> getAllStockBack() {
        return stockBackMapper.selectAll();
    }

    @Override
    public StockBack getStockBackById(Integer id) {
        return stockBackMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addStockBack(StockBack StockBack) {
        stockBackMapper.insertSelective(StockBack);
    }

    @Override
    public void modefyStockBack(StockBack StockBack) {
        stockBackMapper.updateByPrimaryKey(StockBack);
    }

    @Override
    public void deleteStockBackById(Integer id) {
        stockBackMapper.deleteByPrimaryKey(id);
    }

    /**
     * 创建搜索条件
     * @StockBackm StockBack
     * @return
     */
    public Example createCriteria(StockBack stockBack){

        // 创建条件构造器
        Example example = new Example(StockBack.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照参数名查询
        if(null != stockBack.getOrderId()){
            criteria.andEqualTo("orderId", stockBack.getOrderId());
        }

        // 按照模板Id查询
        if(stockBack.getSkuId() != null){
            criteria.andEqualTo("skuId", stockBack.getSkuId());
        }
        return example;
    }

    @Override
    public List<StockBack> searchStockBack(StockBack StockBack) {
        return stockBackMapper.selectByExample(createCriteria(StockBack));
    }

    @Override
    public PageInfo<StockBack> getAllStockBackPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<StockBack> StockBacks = stockBackMapper.selectAll();
        return new PageInfo<>(StockBacks);
    }

    @Override
    public PageInfo<StockBack> searchStockBackPaged(StockBack StockBack, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<StockBack> StockBacks = stockBackMapper.selectByExample(createCriteria(StockBack));
        return new PageInfo<>(StockBacks);
    }
}

package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.StockBack;

import java.util.List;

public interface StockBackService {
    /**
     * 查询所有相册
     * @return
     */
    List<StockBack> getAllStockBack();
    /**
     * 根据Id查询相册
     * @return
     */
    StockBack getStockBackById(Integer id);
    /**
     * 添加相册
     * @return
     */
    void addStockBack(StockBack StockBack);
    /**
     * 修改品牌
     * @return
     */
    void modefyStockBack(StockBack StockBack);
    /**
     * 根据Id删除相册
     * @return
     */
    void deleteStockBackById(Integer id);
    /**
     * 根据条件查询相册
     * @return
     */
    List<StockBack> searchStockBack(StockBack StockBack);
    /**
     * 分页查询所有的相册
     * @return
     */
    PageInfo<StockBack> getAllStockBackPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的品牌
     * @param StockBack
     * @param page
     * @param size
     * @return
     */
    PageInfo<StockBack> searchStockBackPaged(StockBack StockBack, Integer page, Integer size);
}

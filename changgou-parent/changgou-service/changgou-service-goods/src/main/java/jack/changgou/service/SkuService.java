package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Sku;

import java.util.List;

public interface SkuService {
    /**
     * 查询所有Sku
     * @return
     */
    List<Sku> getAllSku();
    /**
     * 根据Id查询Sku
     * @return
     */
    Sku getSkuById(Long id);
    /**
     * 添加Sku
     * @return
     */
    void addSku(Sku sku);
    /**
     * 修改Sku
     * @return
     */
    void modefySku(Sku sku);
    /**
     * 根据Id删除Sku
     * @return
     */
    void deleteSkuById(Long id);
    /**
     * 根据SpuId删除Sku
     * @return
     */
    void deleteSkuBySpuId(Long spuId);
    /**
     * 根据条件查询Sku
     * @return
     */
    List<Sku> searchSku(Sku Sku);
    /**
     * 分页查询所有的Sku
     * @return
     */
    PageInfo<Sku> getAllSkuPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的Sku
     * @param Sku
     * @param page
     * @param size
     * @return
     */
    PageInfo<Sku> searchSkuPaged(Sku Sku, Integer page, Integer size);
}

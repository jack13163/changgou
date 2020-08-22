package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Goods;
import jack.changgou.goods.pojo.Spu;

import java.util.List;

public interface SpuService {
    /**
     * 查询所有Spu
     * @return
     */
    List<Spu> getAllSpu();
    /**
     * 根据Id查询Spu
     * @return
     */
    Spu getSpuById(Long id);
    /**
     * 添加Spu
     * @return
     */
    void addSpu(Spu spu);
    /**
     * 修改Spu
     * @return
     */
    void modefySpu(Spu spu);
    /**
     * 根据Id删除Spu
     * @return
     */
    void deleteSpuById(Long id);
    /**
     * 根据条件查询Spu
     * @return
     */
    List<Spu> searchSpu(Spu spu);
    /**
     * 分页查询所有的Spu
     * @return
     */
    PageInfo<Spu> getAllSpuPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的Spu
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> searchSpuPaged(Spu spu, Integer page, Integer size);
    /**
     * 保存商品
     * @param goods
     * @return
     */
    void saveGoods(Goods goods);
    /**
     * 根据Spu的Id查询商品
     * @param spuId
     * @return
     */
    Goods getGoodsBySpuId(Long spuId);

    /**
     * 商品审核
     * @param spuId
     */
    void audit(Long spuId);
    /**
     * 商品下架
     * @param spuId
     */
    void pull(Long spuId);
    /**
     * 商品上架
     * @param spuId
     */
    void push(Long spuId);
    /**
     * 商品批量上架
     * @param spuIds
     */
    void batchPush(Long[] spuIds);
    /**
     * 商品删除
     * @param spuId
     */
    void deleteGoods(Long spuId);
    /**
     * 商品找回
     * @param spuId
     */
    void findGoods(Long spuId);
}

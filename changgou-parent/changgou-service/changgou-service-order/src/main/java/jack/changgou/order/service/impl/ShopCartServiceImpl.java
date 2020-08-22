package jack.changgou.order.service.impl;

import jack.changgou.goods.feign.SkuFeign;
import jack.changgou.goods.feign.SpuFeign;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.goods.pojo.Spu;
import jack.changgou.order.pojo.OrderItem;
import jack.changgou.order.service.ShopCartService;
import jack.changgou.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SkuFeign skuFeign;

    @Autowired
    SpuFeign spuFeign;

    /**
     * 加入购物车
     * @param id
     * @param num
     * @param username
     */
    @Override
    public void add(Long id, Integer num, String username) {
        String nameSpace = "Cart_" + username;

        // 如果输入的商品数量小于等于0，则移除该商品
        if(num <= 0){
            redisTemplate.boundHashOps(nameSpace).delete(id);

            // 如果此时购物车里没有数据了，则将购物车也一起移除，释放redis中的存储空间
           if(redisTemplate.boundHashOps(nameSpace).size() <= 0) {
               redisTemplate.delete(nameSpace);
           }

            return;
        }

        // 查询商品详情
        Result<Sku> skuResult = skuFeign.getSku(id);
        Sku sku = skuResult.getData();
        Result<Spu> spuResult = spuFeign.getSpu(sku.getSpuId());
        Spu spu = spuResult.getData();

        // 将加入购物车的商品信息封装成OrderItem
        OrderItem orderItem = createOrderItem(num, sku, spu);

        // 将数据存入到redis中
        redisTemplate.boundHashOps(nameSpace).put(id, orderItem);
    }

    /**
     * 查询用户的购物车列表
     * @param username
     * @return
     */
    @Override
    public List<OrderItem> getAll(String username) {
        List values = redisTemplate.boundHashOps("Cart_" + username).values();
        return values;
    }

    private OrderItem createOrderItem(Integer num, Sku sku, Spu spu) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSkuId(sku.getId());
        orderItem.setSpuId(spu.getId());
        orderItem.setImage(spu.getImage());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num * sku.getPrice());
        orderItem.setWeight(sku.getWeight());
        return orderItem;
    }
}

package jack.changgou.seckill.task;

import jack.changgou.seckill.pojo.SeckillGoods;
import jack.changgou.seckill.pojo.SeckillOrder;
import jack.changgou.seckill.service.SeckillGoodsService;
import jack.changgou.vo.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 多线程下单
 */
@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private IdWorker idWorker;

    /**
     * @Async 声明后该方法会异步执行，底层是多线程实现
     */
    @Async
    public void createOrder() {
        try {
            Thread.sleep(10000);
            System.out.println("开始异步下单...");

            // 从redis队列中获取用户排队信息
            SecKillStatus secKillStatus = (SecKillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();

            if (secKillStatus == null) {
                return;
            }

            String namespace = "SeckillGoods_" + secKillStatus.getTime();
            // 从redis中查询商品详情
            SeckillGoods goods = (SeckillGoods) redisTemplate.boundHashOps(namespace).get(secKillStatus.getGoodId());

            // 从列表中获取商品Id，如果获取失败则表示库存不足
            String goodsId = (String) redisTemplate.boundListOps("GoodsList_" + goods.getId()).rightPop();
            // 判断是否有库存
            if (goodsId == null) {
                clearUserQueue(secKillStatus.getUsername());
                throw new RuntimeException("已售罄");
            }

            // 创建订单对象
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setSeckillId(goods.getId());
            seckillOrder.setMoney(goods.getCostPrice());
            seckillOrder.setId(idWorker.nextId());
            seckillOrder.setCreateTime(new Date());
            seckillOrder.setUserId(secKillStatus.getUsername());
            seckillOrder.setStatus("0");//未支付

            /**
             * 将订单对象存储起来
             * 1.一个用户只允许有一个未支付秒杀订单
             * 2.订单存入到Redis：
             * namespace: SeckillOrder
             * key: username
             * value: order对象
             */
            redisTemplate.boundHashOps("SeckillOrder").put(secKillStatus.getUsername(), seckillOrder);

            /**
             * 库存递减
             * Redis：stockCount--
             * 如果已经售罄，则将Redis中的商品信息删除，并将Redis中该商品的数据同步到Mysql
             */
            int size = redisTemplate.boundListOps("GoodsList_" + goods.getId()).size().intValue();
            goods.setStockCount(size);
            if (goods.getStockCount() <= 0) {
                // 同步到MySQL
                seckillGoodsService.update(goods);
                // 删除Redis中的商品数据
                redisTemplate.boundHashOps(namespace).delete(goods.getId());
            } else {
                // 同步数据到redis中
                redisTemplate.boundHashOps(namespace).put(goods.getId() + "", goods);
            }
            // 更新用于查询的订单的状态
            secKillStatus.setOrderId(seckillOrder.getId());
            secKillStatus.setMoney(Float.valueOf(seckillOrder.getMoney()));
            secKillStatus.setStatus(2);// 待支付
            secKillStatus.setGoodId(goods.getId() + "");// 商品编号
            redisTemplate.boundHashOps("SeckillOrderStatus").put(secKillStatus.getUsername(), secKillStatus);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 清空某一个用户排队抢单信息
     * @param username
     */
    private void clearUserQueue(String username) {
        // 清空防止重复抢单的count信息
        redisTemplate.boundHashOps("UserQueueCount").delete(username);
        // 清空用于用户的抢单状态
        redisTemplate.boundHashOps("SeckillOrderStatus").delete(username);
    }
}

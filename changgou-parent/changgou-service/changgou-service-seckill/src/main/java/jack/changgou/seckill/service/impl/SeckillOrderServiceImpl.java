package jack.changgou.seckill.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.seckill.dao.SeckillOrderMapper;
import jack.changgou.seckill.pojo.SeckillGoods;
import jack.changgou.seckill.pojo.SeckillOrder;
import jack.changgou.seckill.service.SeckillGoodsService;
import jack.changgou.seckill.service.SeckillOrderService;
import jack.changgou.seckill.task.MultiThreadingCreateOrder;
import jack.changgou.seckill.task.SecKillStatus;
import jack.changgou.vo.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:SeckillOrder业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;


    /**
     * SeckillOrder条件+分页查询
     *
     * @param seckillOrder 查询条件
     * @param page         页码
     * @param size         页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SeckillOrder> findPage(SeckillOrder seckillOrder, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(seckillOrder);
        //执行搜索
        return new PageInfo<SeckillOrder>(seckillOrderMapper.selectByExample(example));
    }

    /**
     * SeckillOrder分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<SeckillOrder> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<>(seckillOrderMapper.selectAll());
    }

    /**
     * SeckillOrder条件查询
     *
     * @param seckillOrder
     * @return
     */
    @Override
    public List<SeckillOrder> findList(SeckillOrder seckillOrder) {
        //构建查询条件
        Example example = createExample(seckillOrder);
        //根据构建的条件查询数据
        return seckillOrderMapper.selectByExample(example);
    }


    /**
     * SeckillOrder构建查询对象
     *
     * @param seckillOrder
     * @return
     */
    public Example createExample(SeckillOrder seckillOrder) {
        Example example = new Example(SeckillOrder.class);
        Example.Criteria criteria = example.createCriteria();
        if (seckillOrder != null) {
            // 主键
            if (!StringUtils.isEmpty(seckillOrder.getId())) {
                criteria.andEqualTo("id", seckillOrder.getId());
            }
            // 秒杀商品ID
            if (!StringUtils.isEmpty(seckillOrder.getSeckillId())) {
                criteria.andEqualTo("seckillId", seckillOrder.getSeckillId());
            }
            // 支付金额
            if (!StringUtils.isEmpty(seckillOrder.getMoney())) {
                criteria.andEqualTo("money", seckillOrder.getMoney());
            }
            // 用户
            if (!StringUtils.isEmpty(seckillOrder.getUserId())) {
                criteria.andEqualTo("userId", seckillOrder.getUserId());
            }
            // 创建时间
            if (!StringUtils.isEmpty(seckillOrder.getCreateTime())) {
                criteria.andEqualTo("createTime", seckillOrder.getCreateTime());
            }
            // 支付时间
            if (!StringUtils.isEmpty(seckillOrder.getPayTime())) {
                criteria.andEqualTo("payTime", seckillOrder.getPayTime());
            }
            // 状态，0未支付，1已支付
            if (!StringUtils.isEmpty(seckillOrder.getStatus())) {
                criteria.andEqualTo("status", seckillOrder.getStatus());
            }
            // 收货人地址
            if (!StringUtils.isEmpty(seckillOrder.getReceiverAddress())) {
                criteria.andEqualTo("receiverAddress", seckillOrder.getReceiverAddress());
            }
            // 收货人电话
            if (!StringUtils.isEmpty(seckillOrder.getReceiverMobile())) {
                criteria.andEqualTo("receiverMobile", seckillOrder.getReceiverMobile());
            }
            // 收货人
            if (!StringUtils.isEmpty(seckillOrder.getReceiver())) {
                criteria.andEqualTo("receiver", seckillOrder.getReceiver());
            }
            // 交易流水
            if (!StringUtils.isEmpty(seckillOrder.getTransactionId())) {
                criteria.andEqualTo("transactionId", seckillOrder.getTransactionId());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        seckillOrderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改SeckillOrder
     *
     * @param seckillOrder
     */
    @Override
    public void update(SeckillOrder seckillOrder) {
        seckillOrderMapper.updateByPrimaryKey(seckillOrder);
    }

    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 秒杀排队
     *
     * @param username 用户名
     * @param time     时间
     * @param id       商品编号
     */
    @Override
    public boolean add(String username, String time, String id) {
        // 判断是否已经排队，利用redis中的自增，防止用户重复下单
        Long userQueueCount = redisTemplate.boundHashOps("UserQueueCount").increment(username, 1);
        if (userQueueCount > 1) {
            throw new RuntimeException("排队失败，重复排队");
        }

        // 用户抢单排队
        SecKillStatus secKillStatus = new SecKillStatus();
        secKillStatus.setCreateTime(new Date());
        secKillStatus.setGoodId(id);
        secKillStatus.setTime(time);
        secKillStatus.setUsername(username);
        redisTemplate.boundListOps("SeckillOrderQueue").leftPush(secKillStatus);
        // 单独存储一份，用于订单查询，需要同步更新订单的状态
        redisTemplate.boundHashOps("SeckillOrderStatus").put(username, secKillStatus);

        // 执行异步下单操作
        multiThreadingCreateOrder.createOrder();
        return true;
    }

    /**
     * 抢单状态查询
     *
     * @param username 用户名
     */
    @Override
    public SecKillStatus queryStatus(String username) {
        SecKillStatus seckillOrderStatus = (SecKillStatus) redisTemplate.boundHashOps("SeckillOrderStatus").get(username);
        return seckillOrderStatus;
    }

    /**
     * 根据ID查询SeckillOrder
     *
     * @param id
     * @return
     */
    @Override
    public SeckillOrder findById(Long id) {
        return seckillOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询SeckillOrder全部数据
     *
     * @return
     */
    @Override
    public List<SeckillOrder> findAll() {
        return seckillOrderMapper.selectAll();
    }

    /**
     * 更新订单状态
     *
     * @param username
     * @param payTime
     * @param transactionId
     */
    @Override
    public void updateStatus(String username, String payTime, String transactionId) {
        // 查询订单
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(username);
        try {
            seckillOrder.setStatus("1");// 1：已支付
            seckillOrder.setTransactionId(transactionId);
            seckillOrder.setPayTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(payTime));
            // 将订单持久化到数据库中
            seckillOrderMapper.insertSelective(seckillOrder);
            // 删除Redis中的订单
            redisTemplate.boundHashOps("SeckillOrder").delete(username);
            // 删除用户的排队信息
            clearUserQueue(username);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /**
     * 删除用户的订单
     *
     * @param username
     */
    @Override
    public void deleteOrder(String username) {
        // 删除订单
        redisTemplate.boundHashOps("SeckillOrder").delete(username);
        // 查询用户排队信息
        SecKillStatus secKillStatus = (SecKillStatus) redisTemplate.boundHashOps("SeckillOrderStatus").get(username);
        // 删除用户排队信息
        clearUserQueue(username);
        // 回滚库存：redis递增，redis不一定有商品，因此可能需要添加商品到redis
        // 首先获取redis中的商品信息
        String namespace = "SeckillGoods_" + secKillStatus.getTime();
        SeckillGoods goods = (SeckillGoods) redisTemplate.boundHashOps(namespace).get(secKillStatus.getGoodId());
        // 如果商品为空
        if (goods == null) {
            // 从数据库中查询商品信息
            goods = seckillGoodsService.findById(Long.parseLong(secKillStatus.getGoodId()));
            // 库存为1
            goods.setStockCount(1);
            // 更新数据库中的数据
            seckillGoodsService.update(goods);
        }else{
            // 库存加1
            goods.setStockCount(goods.getStockCount() + 1);
        }
        // 写入redis中
        redisTemplate.boundHashOps(namespace).put(goods.getId(), goods);
        // 更新库存队列
        redisTemplate.boundListOps("GoodsList_" + goods.getId()).leftPush(goods.getId());
    }

    /**
     * 清空某一个用户排队抢单信息
     *
     * @param username
     */
    private void clearUserQueue(String username) {
        // 清空防止重复抢单的count信息
        redisTemplate.boundHashOps("UserQueueCount").delete(username);
        // 清空用于用户的抢单状态
        redisTemplate.boundHashOps("SeckillOrderStatus").delete(username);
    }
}

package jack.changgou.seckill.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.seckill.pojo.SeckillOrder;
import jack.changgou.seckill.task.SecKillStatus;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:SeckillOrder业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SeckillOrderService {

    /***
     * SeckillOrder多条件分页查询
     * @param seckillOrder
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(SeckillOrder seckillOrder, int page, int size);

    /***
     * SeckillOrder分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(int page, int size);

    /***
     * SeckillOrder多条件搜索方法
     * @param seckillOrder
     * @return
     */
    List<SeckillOrder> findList(SeckillOrder seckillOrder);

    /***
     * 删除SeckillOrder
     * @param id
     */
    void delete(Long id);

    /***
     * 修改SeckillOrder数据
     * @param seckillOrder
     */
    void update(SeckillOrder seckillOrder);

    /***
     * 新增SeckillOrder
     * @param username 用户名
     * @param time 时间
     * @param id 商品编号
     */
    boolean add(String username, String time, String id);

    /**
     * 抢单状态查询
     *
     * @param username 用户名
     */
    public SecKillStatus queryStatus(String username);

    /**
     * 根据ID查询SeckillOrder
     * @param id
     * @return
     */
     SeckillOrder findById(Long id);

    /***
     * 查询所有SeckillOrder
     * @return
     */
    List<SeckillOrder> findAll();

    /**
     * 更新订单的状态
     * @param username
     * @param payTime
     * @param transactionId
     */
    void updateStatus(String username, String payTime, String transactionId);
    /**
     * 删除订单
     * @param username
     */
    void deleteOrder(String username);
}

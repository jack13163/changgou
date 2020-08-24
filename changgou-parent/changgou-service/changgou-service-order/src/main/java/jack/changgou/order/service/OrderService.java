package jack.changgou.order.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.order.pojo.Order;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:Order业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface OrderService {

    /***
     * Order多条件分页查询
     * @param order
     * @param page
     * @param size
     * @return
     */
    PageInfo<Order> findPage(Order order, int page, int size);

    /***
     * 修改订单状态
     * @param orderId: 订单号
     * @param payTime: 支付时间
     * @param transactionId: 交易流水号
     * @return
     */
    boolean updateStatus(String orderId, String payTime, String transactionId);

    /***
     * 删除
     * @param orderId: 订单号
     * @return
     */
    boolean deleteOrder(String orderId);

    /***
     * Order分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Order> findPage(int page, int size);

    /***
     * Order多条件搜索方法
     * @param order
     * @return
     */
    List<Order> findList(Order order);

    /***
     * 删除Order
     * @param id
     */
    void delete(String id);

    /***
     * 修改Order数据
     * @param order
     */
    void update(Order order);

    /***
     * 新增Order
     * @param order
     */
    void add(Order order);

    /**
     * 根据ID查询Order
     * @param id
     * @return
     */
     Order findById(String id);

    /***
     * 查询所有Order
     * @return
     */
    List<Order> findAll();
}

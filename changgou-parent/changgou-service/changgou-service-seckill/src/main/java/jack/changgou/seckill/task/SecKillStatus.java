package jack.changgou.seckill.task;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户排队抢单信息
 */
public class SecKillStatus implements Serializable {
    private String username;// 秒杀用户
    private Date createTime;// 创建时间
    private int status;// 秒杀状态，1：排队中；2：秒杀等待支付；3：支付超时；4：秒杀失败；5：支付完成
    private String goodId;// 商品Id
    private Float money;// 支付金额
    private Long orderId;// 订单编号
    private String time;// 时间段

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SecKillStatus() {
    }

    public SecKillStatus(String username, Date createTime, int status, String goodId, Float money, Long orderId, String time) {
        this.username = username;
        this.createTime = createTime;
        this.status = status;
        this.goodId = goodId;
        this.money = money;
        this.orderId = orderId;
        this.time = time;
    }
}

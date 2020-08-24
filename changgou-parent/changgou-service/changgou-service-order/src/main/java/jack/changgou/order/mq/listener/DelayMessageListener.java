package jack.changgou.order.mq.listener;

import com.alibaba.fastjson.JSON;
import jack.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 订单信息监听器
 * 监听RabbitMq
 */
@Component
@RabbitListener(queues = "orderListenerQueue")
public class DelayMessageListener {

    @Autowired
    OrderService orderService;

    /**
     * 监听处理方法
     */
    @RabbitHandler
    public void handleMessage(String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        System.out.println(simpleDateFormat.format(new Date()) + ": 监听到过期的订单[" + message + "]");
    }
}

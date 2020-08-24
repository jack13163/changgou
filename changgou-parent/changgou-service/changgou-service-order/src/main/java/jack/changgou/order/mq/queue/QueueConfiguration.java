package jack.changgou.order.mq.queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 利用rabbitmq的延时队列机制实现库存回滚，取消订单
 * 利用延时机制
 */
@Configuration
public class QueueConfiguration {

    /**
     * 创建一个会过期的延时队列，过期后会将数据发送到另一个队列
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder.durable("orderDelayQueue")
                .withArgument("x-dead-letter-exchange", "orderListenerExchange")// 消息超时进入死信队列，绑定死信队列交换机
                .withArgument("x-dead-letter-routing-key", "orderListenerQueue").build();// 绑定指定的路径键
    }

    /**
     * 创建一个接收过期消息的普通队列
     *
     * @return
     */
    @Bean
    public Queue orderListenerQueue() {
        return new Queue("orderListenerQueue", true);
    }


    /**
     * 创建交换机
     */
    @Bean
    public DirectExchange orderListenerExchange() {
        return new DirectExchange("orderListenerExchange");
    }


    /**
     * 绑定交换机和队列
     */
    @Bean
    public Binding orderListenerExchangeBinding(Queue orderListenerQueue, Exchange orderListenerExchange) {
        return BindingBuilder.bind(orderListenerQueue).to(orderListenerExchange).with("orderListenerQueue").noargs();
    }

}

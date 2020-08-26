package jack.changgou.pay.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建支付相关的详细队列
 * mq:
 *   pay:
 *     exchange:
 *       order: exchange.order
 *       seckillorder: exchange.seckillorder
 *     queue:
 *       order: queue.order
 *       seckillorder: queue.seckillorder
 *     routing:
 *       key: queue.order
 *       seckillkey: queue.seckillorder
 */
@Configuration
public class MQConfig {

    /**
     * 创建普通订单队列
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {
        return new Queue("queue.order", true);
    }

    /**
     * 创建秒杀订单队列
     *
     * @return
     */
    @Bean
    public Queue seckillOrderQueue() {
        return new Queue("queue.seckillorder", true);
    }

    /**
     * 创建普通订单交换机
     *
     * @return
     */
    @Bean
    public DirectExchange orderListenerExchange() {
        return new DirectExchange("exchange.order");
    }


    /**
     * 创建秒杀订单交换机
     */
    @Bean
    public DirectExchange seckillOrderListenerExchange() {
        return new DirectExchange("exchange.seckillorder");
    }


    /**
     * 绑定普通订单交换机和队列
     */
    @Bean
    public Binding orderListenerExchangeBinding(Queue orderQueue, Exchange orderListenerExchange) {
        return BindingBuilder.bind(orderQueue).to(orderListenerExchange).with("queue.order").noargs();
    }

    /**
     * 绑定秒杀订单交换机和队列
     */
    @Bean
    public Binding seckillOrderListenerExchangeBinding(Queue seckillOrderQueue, Exchange seckillOrderListenerExchange) {
        return BindingBuilder.bind(seckillOrderQueue).to(seckillOrderListenerExchange).with("queue.seckillorder").noargs();
    }
}

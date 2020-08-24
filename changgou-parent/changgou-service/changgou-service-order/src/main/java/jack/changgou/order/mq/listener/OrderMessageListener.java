package jack.changgou.order.mq.listener;

import com.alibaba.fastjson.JSON;
import com.netflix.discovery.converters.Auto;
import jack.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 订单信息监听器
 * 监听RabbitMq
 */
@Component
@RabbitListener(queues = "${mq.pay.queue.order}")
public class OrderMessageListener {

    @Autowired
    OrderService orderService;

    /**
     * 监听处理方法
     */
    @RabbitHandler
    public void handleMessage(String message) {
        System.out.println("从rabbit mq中监听到支付消息: " + message);

        Map<String, String> map = JSON.parseObject(message, Map.class);
        // {transaction_id=4200000722202008245726265459, nonce_str=0fb0339a26b6413aaab22acf50ddd168, bank_type=ICBC_DEBIT, openid=oNpSGwThOWn21gnod0Ou_PlDzBss, sign=360C64BD2AA9E5F6C88FD483F1D64E93, fee_type=CNY, mch_id=1473426802, cash_fee=1, out_trade_no=198529899524485, appid=wx8397f8696b538317, total_fee=1, trade_type=NATIVE, result_code=SUCCESS, time_end=20200824103852, is_subscribe=N, return_code=SUCCESS}
        if (map != null) {
            if (map.get("return_code") != null && map.get("return_code").equals("SUCCESS")) {
                String orderId = map.get("out_trade_no");

                if (map.get("result_code") != null && map.get("result_code").equals("SUCCESS")) {
                    // 支付成功，获取微信生成的流水交易号和商品订单号
                    String transactionId = map.get("transaction_id");
                    String payTime = map.get("time_end");

                    orderService.updateStatus(orderId, payTime, transactionId);
                } else {
                    // 关闭支付，若关闭支付失败，则说明正好此时用户已经完成了支付，此时更新订单的状态即可

                    // 支付失败，取消订单，回滚库存
                    orderService.deleteOrder(orderId);
                }
            }
        }
    }
}

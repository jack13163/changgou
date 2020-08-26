package jack.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import jack.changgou.pay.service.WxPayService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weixin/pay")
public class WxPayController {

    @Autowired
    WxPayService wxPayService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 生成支付二维码
     * <p>
     * attach字段可以携带自定义的参数（exchange+routingkey）：
     * 普通订单：
     * exchange: exchange.order
     * routingkey: queue.order
     * 秒杀订单：
     * exchange: exchange.seckillorder
     * routingkey: queue.seckillorder
     *
     * @return
     */
    @RequestMapping("/create/native")
    public Result createQrCode(@RequestParam Map<String, String> parameters) {
        Map<String, String> resultMap = wxPayService.ceateQrCode(parameters);
        return new Result(true, StatusCode.OK, "生成支付二维码成功", resultMap);
    }

    /**
     * 查询微信订单
     *
     * @return
     */
    @RequestMapping("/query/order")
    public Result queryOrder(@RequestParam String orderId) {
        Map<String, String> resultMap = wxPayService.queryWxPayOrder(orderId);
        return new Result(true, StatusCode.OK, "查询微信订单成功", resultMap);
    }


    /**
     * 支付结果通知回调
     * <p>
     * 注意权限问题：
     * <p>
     * 1.放行当前请求；
     * 2.禁用csrf保护。
     *
     * @return
     */
    @RequestMapping("/notify/url")
    public String notifyUrl(HttpServletRequest request) throws Exception {
        // 获取支付结果
        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String result = new String(bytes, "UTF-8");

        Map<String, String> map = null;
        if (!StringUtils.isEmpty(result)) {
            map = WXPayUtil.xmlToMap(result);
            // {transaction_id=4200000722202008245726265459, nonce_str=0fb0339a26b6413aaab22acf50ddd168, bank_type=ICBC_DEBIT, openid=oNpSGwThOWn21gnod0Ou_PlDzBss, sign=360C64BD2AA9E5F6C88FD483F1D64E93, fee_type=CNY, mch_id=1473426802, cash_fee=1, out_trade_no=198529899524485, appid=wx8397f8696b538317, total_fee=1, trade_type=NATIVE, result_code=SUCCESS, time_end=20200824103852, is_subscribe=N, return_code=SUCCESS}
            System.out.println(map);
        } else {
            map = mapStringToMap("{transaction_id=4200000722202008245726265459, nonce_str=0fb0339a26b6413aaab22acf50ddd168, bank_type=ICBC_DEBIT, openid=oNpSGwThOWn21gnod0Ou_PlDzBss, sign=360C64BD2AA9E5F6C88FD483F1D64E93, fee_type=CNY, mch_id=1473426802, cash_fee=1, out_trade_no=198529899524485, appid=wx8397f8696b538317, total_fee=1, trade_type=NATIVE, result_code=SUCCESS, time_end=20200824103852, is_subscribe=N, return_code=SUCCESS}");
        }

        // 获取自定义参数，发送消息到不同的队列
        String attach = map.get("attach");
        Map<String, String> attachMap = JSON.parseObject(attach, Map.class);
        String exchangeName = attachMap.get("exchange");
        String routingKey = attachMap.get("routingkey");
        rabbitTemplate.convertAndSend(exchangeName, routingKey, JSON.toJSONString(map));

        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1].trim();
            map.put(key, value);
        }
        return map;
    }
}

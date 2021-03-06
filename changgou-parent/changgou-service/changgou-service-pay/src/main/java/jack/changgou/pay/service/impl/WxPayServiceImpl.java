package jack.changgou.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import jack.changgou.pay.service.WxPayService;
import jack.changgou.vo.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxPayServiceImpl implements WxPayService {

    @Value("${weixin.appid}")
    private String appId;

    @Value("${weixin.partner}")
    private String partner;

    @Value("${weixin.partnerkey}")
    private String partnerkey;

    @Value("${weixin.notifyurl}")
    private String notifyurl;

    /**
     * 创建支付二维码
     * <p>
     * attach字段可以携带自定义的参数（exchange+routingkey）：
     * 普通订单：
     * exchange: exchange.order
     * routingkey: queue.order
     * 秒杀订单：
     * exchange: exchange.seckillorder
     * routingkey: queue.seckillorder
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, String> ceateQrCode(Map<String, String> map) {
        Map<String, String> resultMap = null;

        try {
            // 根据这两个字段区分是普通订单还是秒杀订单，消息发给不同的rabbitmq中；
            // 秒杀微服务和普通订单微服务分别订阅不同的队列即可。
            String exchange = map.get("exchange");
            String routingkey = map.get("routingkey");

            Map<String, String> paremeterMap = new HashMap<>();
            paremeterMap.put("appid", appId);// 公众账号
            paremeterMap.put("mch_id", partner);// 商户号
            paremeterMap.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
            paremeterMap.put("body", "畅购商城商品");// 商品描述
            paremeterMap.put("out_trade_no", map.get("orderId"));// 商户订单号
            paremeterMap.put("total_fee", map.get("totalFee"));// 总金额
            paremeterMap.put("spbill_create_ip", "127.0.0.1");// 终端ip地址
            paremeterMap.put("notify_url", notifyurl);// 通知地址
            paremeterMap.put("trade_type", "NATIVE");// 交易类型

            HashMap<String, String> attachMap = new HashMap<>();
            attachMap.put("exchange", exchange);
            attachMap.put("routingkey", routingkey);
            // 如果是秒杀订单，需要传递username属性
            String username = map.get("username");
            if(!StringUtils.isEmpty(username)){
                attachMap.put("username", username);
            }
            paremeterMap.put("attach", JSON.toJSONString(attachMap));// attach附加数据

            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            resultMap = sendPostRequest(paremeterMap, url);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return resultMap;
    }

    /**
     * 发送Post请求
     *
     * @param paremeterMap
     * @param url
     * @return
     * @throws Exception
     */
    private Map<String, String> sendPostRequest(Map<String, String> paremeterMap, String url) throws Exception {
        Map<String, String> resultMap;
        String xml = WXPayUtil.generateSignedXml(paremeterMap, partnerkey);

        HttpClient httpClient = new HttpClient(url);
        httpClient.setHttps(true);
        httpClient.setXmlParam(xml);
        httpClient.post();

        String result = httpClient.getContent();
        resultMap = WXPayUtil.xmlToMap(result);
        return resultMap;
    }

    /**
     * 查询微信支付订单
     *
     * @param orderId
     * @return
     */
    @Override
    public Map<String, String> queryWxPayOrder(String orderId) {
        Map<String, String> resultMap = null;

        Map<String, String> paremeterMap = new HashMap<>();
        paremeterMap.put("appid", appId);// 公众账号
        paremeterMap.put("mch_id", partner);// 商户号
        paremeterMap.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
        paremeterMap.put("out_trade_no", orderId);// 商户订单号

        try {
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            resultMap = sendPostRequest(paremeterMap, url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return resultMap;
    }
}

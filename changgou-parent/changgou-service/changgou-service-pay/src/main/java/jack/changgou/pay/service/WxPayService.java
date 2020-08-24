package jack.changgou.pay.service;

import java.util.Map;

public interface WxPayService {
    // 创建支付二维码
    Map<String, String> ceateQrCode(Map<String, String> map);
    // 查询微信订单
    Map<String, String> queryWxPayOrder(String orderId);
}

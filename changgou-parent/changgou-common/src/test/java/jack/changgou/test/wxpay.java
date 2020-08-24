package jack.changgou.test;

import com.github.wxpay.sdk.WXPayUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付SDK的主要作用是：
 * 数据的封装和解析
 *
 * 微信支付测试
 */
public class wxpay {
    /**
     * 测试
     */
    @Test
    public void testDemo() throws Exception {
        // 生成随机字符串
        String str = WXPayUtil.generateNonceStr();
        System.out.println(str);

        // 将map转换为xml字符串
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("id", "No.343");
        dataMap.put("title", "订单");
        dataMap.put("orderId", "192220544531");
        dataMap.put("money", "343");
        String xml = WXPayUtil.mapToXml(dataMap);
        System.out.println(xml);

        // 将map转换为xml，并生成签名，默认为MD5加盐加密
        String signedXml = WXPayUtil.generateSignedXml(dataMap, "itcast");
        System.out.println(signedXml);
        
        // 将xml转换为map
        Map<String, String> map = WXPayUtil.xmlToMap(xml);
        System.out.println(map);

    }
}

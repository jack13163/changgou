package jack.changgou.test;

import jack.changgou.vo.HttpClient;
import org.junit.Test;

import java.io.IOException;

/**
 * HttpClient使用案例
 */
public class HttpClientTest {
    @Test
    public void testHttpClient() throws IOException {
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        HttpClient client = new HttpClient(url);
        /**
         * 参数封装
         * <xml>
         *    <appid>wx2421b1c4370ec43b</appid>
         *    <mch_id>10000100</mch_id>
         *    <nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str>
         *    <transaction_id>1008450740201411110005820873</transaction_id>
         *    <sign>FDD167FAA73459FD921B144BAF4F4CA2</sign>
         * </xml>
         */
        String xml = "<xml>\n" +
                "   <appid>wx2421b1c4370ec43b</appid>\n" +
                "   <mch_id>10000100</mch_id>\n" +
                "   <nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str>\n" +
                "   <transaction_id>1008450740201411110005820873</transaction_id>\n" +
                "   <sign>FDD167FAA73459FD921B144BAF4F4CA2</sign>\n" +
                "</xml>";
        client.setXmlParam(xml);
        client.setHttps(true);
        client.post();

        String content = client.getContent();
        System.out.println(content);


    }
}

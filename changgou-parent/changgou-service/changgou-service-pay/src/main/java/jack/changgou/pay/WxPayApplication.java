package jack.changgou.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})// 没有排除datasource
public class WxPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxPayApplication.class, args);
    }
}

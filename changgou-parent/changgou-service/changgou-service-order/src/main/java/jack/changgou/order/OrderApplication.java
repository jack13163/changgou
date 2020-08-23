package jack.changgou.order;


import jack.changgou.vo.FeignRequestInterceptor;
import jack.changgou.vo.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"jack.changgou.order.dao"})
@EnableFeignClients(basePackages = {"jack.changgou.goods.feign"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     * 解决微服务之间的服务调用问题
     * @return
     */
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor(){
        return new FeignRequestInterceptor();
    }

    /**
     * Id生成器
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0, 0);
    }
}

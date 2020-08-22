package jack.changgou;

import jack.changgou.vo.FeignRequestInterceptor;
import jack.changgou.vo.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("jack.changgou.dao") // 开启通用Mapper的包扫描
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

    /**
     * Id生成器
     * @return
     */
    @Bean
    public IdWorker IdWorker(){
        return new IdWorker();
    }


    /**
     * 将Feign拦截器注入到容器中
     * 解决微服务之间的服务调用问题，当前微服务Feign调用其他微服务时会将自己的令牌传递过去
     * @return
     */
    @Bean
    public FeignRequestInterceptor feignInterceptor(){
        return new FeignRequestInterceptor();
    }
}

package com.changgou.user;

import jack.changgou.vo.FeignRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.changgou.user.dao")// 通用Mapper的扫描包
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
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

package jack.changgou.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"jack.changgou.seckill.dao"})
@EnableScheduling// 开启定时任务功能
public class SecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }
}

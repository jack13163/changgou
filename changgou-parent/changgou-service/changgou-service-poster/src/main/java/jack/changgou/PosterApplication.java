package jack.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("jack.changgou.dao") // 开启通用Mapper的包扫描
public class PosterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PosterApplication.class, args);
    }
}

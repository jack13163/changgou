package jack.changgou.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 商品搜索实现过程：
 * 1.创建一个javabean对象，建立于索引库的映射关系；
 * 2.在goods-service-api中创建Feign，查询所有的商品Sku集合；
 * 3.在搜索为服务中调用Feign，查询所有的Sku集合，并且将Sku集合转换为SkuInfo集合；
 * 4.controller->service->dao，实现数据导入到ES中。
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})// 调用feign查询商品信息
@EnableEurekaClient
@EnableFeignClients(basePackages = {"jack.changgou.goods.feign"})// 配置Feign的扫描包路径
@EnableElasticsearchRepositories(basePackages = {"jack.changgou.search.dao"})// 配置elasticsearch扫描
public class SearchApplication {
    public static void main(String[] args) {
        // 解决elasticsearch和netty的冲突
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchApplication.class, args);
    }
}

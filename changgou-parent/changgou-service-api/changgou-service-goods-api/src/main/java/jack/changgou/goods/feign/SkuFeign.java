package jack.changgou.goods.feign;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// 告诉Feign调用的微服务的名称
@FeignClient("goods")
@RequestMapping("/sku")
public interface SkuFeign {
    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    Result<PageInfo<Sku>> searchSkuPaged(@PathVariable("page") Integer page, @PathVariable("size") Integer size);

    /**
     * 根据id查询商品详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Sku> getSku(@PathVariable("id") Long id);

    /**
     * 减少商品库存
     * key: 商品Id
     * value: 递减的数量
     * @RequestParam 接收的参数是来自requestHeader中，即请求头。
     * 用来处理 Content-Type 为 application/x-www-form-urlencoded 编码的内容，Content-Type默认为该属性。
     *
     * @RequestBody 接收的参数是来自requestBody中，即请求体。一般用于处理非 Content-Type: application/x-www-form-urlencoded编码格式的数据，
     * 比如：application/json、application/xml等类型的数据。
     * 就application/json类型的数据而言，使用注解@RequestBody可以将body里面所有的json数据传到后端，后端再进行解析。
     *
     * @param map
     * @return
     */
    @GetMapping("/decr/count")
    Result decrCount(@RequestParam Map<String, Integer> map);
}
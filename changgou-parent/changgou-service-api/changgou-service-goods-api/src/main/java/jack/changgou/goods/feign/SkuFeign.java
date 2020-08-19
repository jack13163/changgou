package jack.changgou.goods.feign;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

package jack.changgou.search.feign;

import jack.changgou.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@FeignClient("search")// 指定服务名字
@RequestMapping("/search")// 指定调用方法
public interface SkuFeign {
    /**
     * 根据关键词进行条件搜索
     * @param conditionMap
     * @return
     */
    @GetMapping("/condition")
    Map<String, Object> search(@RequestParam Map<String, String> conditionMap) throws IOException;

    /**
     * 导入数据到elasticsearch
     * @return
     */
    @GetMapping("/import")
    Result importSkuInfo();
}

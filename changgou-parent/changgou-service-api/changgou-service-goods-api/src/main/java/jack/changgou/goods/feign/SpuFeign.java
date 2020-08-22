package jack.changgou.goods.feign;


import jack.changgou.goods.pojo.Spu;
import jack.changgou.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("goods")
@RequestMapping("/spu")
public interface SpuFeign {
    // 查询所有的spu信息
    @GetMapping
    Result<List<Spu>> allSpu();

    // 根据id查询指定spu信息
    @GetMapping("/{id}")
    Result<Spu> getSpu(@PathVariable("id") Long id);
}

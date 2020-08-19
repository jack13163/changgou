package jack.changgou.search.controller;

import jack.changgou.search.service.SkuInfoService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SkuInfoController {
    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 根据关键词进行条件搜索
     *
     * @param conditionMap
     * @return
     */
    @GetMapping("/condition")
    public Map<String, Object> search(@RequestParam Map<String, String> conditionMap) throws IOException {
        return skuInfoService.searchByKeyword(conditionMap);
    }

    /**
     * 导入数据到elasticsearch
     *
     * @return
     */
    @GetMapping("/import")
    public Result importSkuInfo() {
        skuInfoService.importSkuInfo();
        return new Result<>(true, StatusCode.OK, "成功将数据库中的数据导入到elasticsearch中", null);
    }
}

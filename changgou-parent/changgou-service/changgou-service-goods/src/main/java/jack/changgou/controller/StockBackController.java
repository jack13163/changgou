package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.StockBack;
import jack.changgou.service.StockBackService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stock")
@CrossOrigin // 解决跨域问题
public class StockBackController {
    @Autowired
    StockBackService stockBackService;

    @GetMapping
    public Result<List<StockBack>> allStockBack() {
        List<StockBack> StockBacks = stockBackService.getAllStockBack();
        Result<List<StockBack>> result = new Result<>(true, StatusCode.OK, "查询参数数据成功", StockBacks);
        return result;
    }

    @GetMapping("/{id}")
    public Result<StockBack> getStockBack(@PathVariable("id") Integer id) {
        StockBack StockBack = stockBackService.getStockBackById(id);
        Result<StockBack> result = null;
        if (StockBack != null) {
            result = new Result<>(true, StatusCode.OK, "查询参数数据成功", StockBack);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到参数数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getStockBack(@RequestBody StockBack StockBack) {
        stockBackService.addStockBack(StockBack);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入参数成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @return
     * @StockBackm id
     * @StockBackm StockBack
     */
    @PutMapping("/{id}")
    public Result<String> modefyStockBack(@PathVariable("id") String id, @RequestBody StockBack stockBack) {
        stockBack.setOrderId(id);
        stockBackService.modefyStockBack(stockBack);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改参数成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyStockBack(@PathVariable("id") Integer id) {
        stockBackService.deleteStockBackById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除参数成功", null);
        return result;
    }

    /**
     * 根据参数名和模板Id搜索
     *
     * @return
     * @StockBackm StockBack
     */
    @PostMapping("/search")
    public Result<List<StockBack>> searchSpec(@RequestBody StockBack StockBack) {
        List<StockBack> brands = stockBackService.searchStockBack(StockBack);
        Result<List<StockBack>> result = new Result<>(true, StatusCode.OK, "根据条件查询参数成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<StockBack>> searchSpecPaged(@PathVariable("page") Integer page,
                                                       @PathVariable("size") Integer size) {
        PageInfo<StockBack> StockBackPageInfo = stockBackService.getAllStockBackPaged(page, size);
        Result<PageInfo<StockBack>> result = new Result<>(true, StatusCode.OK, "根据条件查询参数成功", StockBackPageInfo);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<StockBack>> searchSpecPaged(@RequestBody StockBack StockBack,
                                                       @PathVariable("page") Integer page,
                                                       @PathVariable("size") Integer size) {
        PageInfo<StockBack> StockBackPageInfo = stockBackService.searchStockBackPaged(StockBack, page, size);
        Result<PageInfo<StockBack>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询参数成功", StockBackPageInfo);
        return result;
    }
}

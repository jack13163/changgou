package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.service.SkuService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    SkuService SkuService;

    @GetMapping
    public Result<List<Sku>> allSku() {
        List<Sku> Skus = SkuService.getAllSku();
        Result<List<Sku>> result = new Result<>(true, StatusCode.OK, "查询商品Sku数据成功", Skus);
        return result;
    }

    /**
     * 商品库存递减
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
    public Result decrCount(@RequestParam Map<String, Integer> map){
        SkuService.decrCount(map);

        Result<String> result = new Result<>(true, StatusCode.OK, "商品Sku库存减少成功");
        return result;
    }

  @GetMapping("/{id}")
    public Result<Sku> getSku(@PathVariable("id") Long id) {
        Sku Sku = SkuService.getSkuById(id);
        Result<Sku> result = null;
        if (Sku != null) {
            result = new Result<>(true, StatusCode.OK, "查询指定商品Sku数据成功", Sku);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前商品Sku数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getSku(@RequestBody Sku Sku) {
        SkuService.addSku(Sku);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入商品Sku信息成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param Sku
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefySku(@PathVariable("id") Long id, @RequestBody Sku Sku) {
        Sku.setId(id);
        SkuService.modefySku(Sku);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改商品Sku信息成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyBrand(@PathVariable("id") Long id) {
        SkuService.deleteSkuById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除商品Sku成功", null);
        return result;
    }

    /**
     * 根据商品Sku名和首字母搜索
     *
     * @param Sku
     * @return
     */
    @PostMapping("/search")
    public Result<List<Sku>> searchSku(@RequestBody Sku Sku) {
        List<Sku> brands = SkuService.searchSku(Sku);
        Result<List<Sku>> result = new Result<>(true, StatusCode.OK, "根据条件查询商品Sku成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Sku>> searchSkuPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Sku> Skus = SkuService.getAllSkuPaged(page, size);
        Result<PageInfo<Sku>> result = new Result<>(true, StatusCode.OK, "根据条件查询商品Sku成功", Skus);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Sku>> searchSkuPaged(@RequestBody Sku Sku,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Sku> brands = SkuService.searchSkuPaged(Sku, page, size);
        Result<PageInfo<Sku>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询商品Sku成功", brands);
        return result;
    }

}

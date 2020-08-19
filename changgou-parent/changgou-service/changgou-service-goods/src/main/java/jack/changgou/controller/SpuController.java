package jack.changgou.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Goods;
import jack.changgou.goods.pojo.Sku;
import jack.changgou.goods.pojo.Spu;
import jack.changgou.service.SpuService;
import jack.changgou.vo.IdWorker;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    SpuService spuService;

    @GetMapping
    public Result<List<Spu>> allSpu() {
        List<Spu> spus = spuService.getAllSpu();
        Result<List<Spu>> result = new Result<>(true, StatusCode.OK, "查询商品Spu数据成功", spus);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Spu> getSpu(@PathVariable("id") Integer id) {
        Spu spu = spuService.getSpuById(id);
        Result<Spu> result = null;
        if (spu != null) {
            result = new Result<>(true, StatusCode.OK, "查询指定商品Spu数据成功", spu);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前商品Spu数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getSpu(@RequestBody Spu spu) {
        spuService.addSpu(spu);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入商品Spu信息成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param spu
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefySpu(@PathVariable("id") Long id, @RequestBody Spu spu) {
        spu.setId(id);
        spuService.modefySpu(spu);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改商品Spu信息成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyBrand(@PathVariable("id") Integer id) {
        spuService.deleteSpuById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除商品Spu成功", null);
        return result;
    }

    /**
     * 根据商品Spu名和首字母搜索
     *
     * @param spu
     * @return
     */
    @PostMapping("/search")
    public Result<List<Spu>> searchSpu(@RequestBody Spu spu) {
        List<Spu> brands = spuService.searchSpu(spu);
        Result<List<Spu>> result = new Result<>(true, StatusCode.OK, "根据条件查询商品Spu成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Spu>> searchSpuPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Spu> Spus = spuService.getAllSpuPaged(page, size);
        Result<PageInfo<Spu>> result = new Result<>(true, StatusCode.OK, "根据条件查询商品Spu成功", Spus);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Spu>> searchSpuPaged(@RequestBody Spu spu,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Spu> brands = spuService.searchSpuPaged(spu, page, size);
        Result<PageInfo<Spu>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询商品Spu成功", brands);
        return result;
    }

    @PostMapping("/goods")
    public Result<String> saveGoods(@RequestBody Goods goods) {
        spuService.saveGoods(goods);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入商品Spu信息成功", null);
        return result;
    }

    @GetMapping("/goods/{id}")
    public Result<Goods> getGoodsBySpuId(@PathVariable("id") Long spuId) {
        Goods goods = spuService.getGoodsBySpuId(spuId);
        Result<Goods> result = new Result<>(true, StatusCode.OK, "根据SpuId获取商品信息" + (goods == null? "失败" : "成功"), goods);
        return result;
    }

    /**
     * 审核商品
     * @param spuId
     * @return
     */
    @PutMapping("/audit/{id}")
    public Result<String> audit(@PathVariable("id") Long spuId){
        spuService.audit(spuId);
        Result<String> result = new Result<>(true, StatusCode.OK, "商品信息审核成功");
        return result;
    }

    /**
     * 删除商品
     * @param spuId
     * @return
     */
    @DeleteMapping("/goods/{id}")
    public Result<String> deleteGoods(@PathVariable("id") Long spuId){
        spuService.deleteGoods(spuId);
        Result<String> result = new Result<>(true, StatusCode.OK, "商品删除成功");
        return result;
    }

    /**
     * 找回商品
     * @param spuId
     * @return
     */
    @PutMapping("/goods/find/{id}")
    public Result<String> findGoods(@PathVariable("id") Long spuId){
        spuService.findGoods(spuId);
        Result<String> result = new Result<>(true, StatusCode.OK, "商品找回成功");
        return result;
    }

    /**
     * 下架商品
     * @param spuId
     * @return
     */
    @PutMapping("/pull/{id}")
    public Result<String> pull(@PathVariable("id") Long spuId){
        spuService.pull(spuId);
        Result<String> result = new Result<>(true, StatusCode.OK, "商品下架成功");
        return result;
    }

    /**
     * 上架商品
     * @param spuId
     * @return
     */
    @PutMapping("/push/{id}")
    public Result<String> push(@PathVariable("id") Long spuId){
        spuService.push(spuId);
        Result<String> result = new Result<>(true, StatusCode.OK, "商品上架成功");
        return result;
    }

    /**
     * 批量上架商品
     * @param spuIds
     * @return
     */
    @PutMapping("/push/batch")
    public Result<String> batchPush(@RequestBody Long[] spuIds){
        spuService.batchPush(spuIds);
        Result<String> result = new Result<>(true, StatusCode.OK, "商品批量上架成功");
        return result;
    }

    @GetMapping("/goods/test")
    public Result<Goods> testGoods() {
        // 利用雪花算法生成ID
        IdWorker idWorker = new IdWorker();

        Goods goods = new Goods();

        Spu spu = new Spu();
        spu.setId(idWorker.nextId());
        spu.setCaption("华为P30 Pro");
        spu.setName("华为P30 Pro");
        spu.setBrandId(1);
        spu.setCategory1Id(15);
        spu.setCategory2Id(22);
        spu.setCategory3Id(36);
        spu.setCommentNum(0);
        spu.setFreightId(1);// 运费模板Id
        spu.setImage("http://192.168.137.118:8080/group1/M00/00/01/wKiJdl8yCI6AH6arAADPKuyZj-w563.jpg");
        spu.setSaleNum(0);
        spu.setImages("http://192.168.137.118:8080/group1/M00/00/01/wKiJdl8yCI6AH6arAADPKuyZj-w563.jpg,http://192.168.137.118:8080/group1/M00/00/01/wKiJdl8yCIKAcU68AAC7O1DeRg0815.png");
        spu.setIsEnableSpec("0");// 是否启用规格
        spu.setParaItems("厂商:深圳华强北,保修:3年");// 参数
        spu.setSpecItems("内存:4G,CPU:I5");// 规格
        spu.setIsDelete("0");// 删除标记
        spu.setIsEnableSpec("0");// 是否启用规格
        spu.setSaleService("免费包邮");// 服务
        spu.setSn("No90001");// sn序列号
        spu.setTemplateId(1);// 模板编号
        spu.setStatus("0");// 审核状态
        spu.setIsMarketable("0");// 是否上架
        spu.setIntroduction("欢迎购买");
        goods.setSpu(spu);

        Date date = new Date();
        List<Sku> skuList = new ArrayList<>();
        List<String> colors = Arrays.asList("天空蓝", "宝丝绿", "炫彩");
        for (int i = 0; i < 3; i++) {
            Sku sku = new Sku();
            sku.setId(idWorker.nextId());
            sku.setName("华为P30 Pro " + colors.get(i));
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setSpuId(spu.getId());
            sku.setNum(999);
            sku.setAlertNum(90);
            sku.setImage("http://192.168.137.118:8080/group1/M00/00/01/wKiJdl8yCI6AH6arAADPKuyZj-w563.jpg");
            sku.setImages("http://192.168.137.118:8080/group1/M00/00/01/wKiJdl8yCI6AH6arAADPKuyZj-w563.jpg,http://192.168.137.118:8080/group1/M00/00/01/wKiJdl8yCIKAcU68AAC7O1DeRg0815.png");
            sku.setCategoryId(26);
            sku.setSpec("内存:4G,CPU:I5");
            sku.setPrice(3999);
            sku.setWeight(1000);
            sku.setSaleNum(0);
            sku.setCommentNum(0);
            sku.setStatus("0");
            // 分类名和分类Id
            sku.setCategoryId(1);
            sku.setCategoryName("手机");
            // 品牌名
            sku.setBrandName("华为");
            skuList.add(sku);
        }
        goods.setSkuList(skuList);

        String s = JSON.toJSONString(goods);
        System.out.println(s);
        Result<Goods> result = new Result<>(true, StatusCode.OK, "根据SpuId获取商品信息成功", goods);
        return result;
    }

}

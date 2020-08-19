package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Pref;
import jack.changgou.service.PrefService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pref")
public class PrefController {

    @Autowired
    PrefService PrefService;

    @GetMapping
    public Result<List<Pref>> allPref() {
        List<Pref> Prefs = PrefService.getAllPref();
        Result<List<Pref>> result = new Result<>(true, StatusCode.OK, "查询订单数据成功", Prefs);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Pref> getPref(@PathVariable("id") Integer id) {
        Pref Pref = PrefService.getPrefById(id);
        Result<Pref> result = null;
        if (Pref != null) {
            result = new Result<>(true, StatusCode.OK, "查询指定订单数据成功", Pref);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前订单数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getPref(@RequestBody Pref Pref) {
        PrefService.addPref(Pref);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入订单信息成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param Pref
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyPref(@PathVariable("id") Integer id, @RequestBody Pref Pref) {
        Pref.setId(id);
        PrefService.modefyPref(Pref);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改订单信息成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyBrand(@PathVariable("id") Integer id) {
        PrefService.deletePrefById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除订单成功", null);
        return result;
    }

    /**
     * 根据品牌名和首字母搜索
     *
     * @param Pref
     * @return
     */
    @PostMapping("/search")
    public Result<List<Pref>> searchPref(@RequestBody Pref Pref) {
        List<Pref> brands = PrefService.searchPref(Pref);
        Result<List<Pref>> result = new Result<>(true, StatusCode.OK, "根据条件查询品牌成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Pref>> searchPrefPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Pref> Prefs = PrefService.getAllPrefPaged(page, size);
        Result<PageInfo<Pref>> result = new Result<>(true, StatusCode.OK, "根据条件查询品牌成功", Prefs);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Pref>> searchPrefPaged(@RequestBody Pref Pref,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Pref> brands = PrefService.searchPrefPaged(Pref, page, size);
        Result<PageInfo<Pref>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询品牌成功", brands);
        return result;
    }

}

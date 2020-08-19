package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Spec;
import jack.changgou.service.SpecService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/spec")
@CrossOrigin // 解决跨域问题
public class SpecController {
    @Autowired
    SpecService specService;

    @GetMapping
    public Result<List<Spec>> allSpec() {
        List<Spec> specs = specService.getAllSpec();
        Result<List<Spec>> result = new Result<>(true, StatusCode.OK, "查询规格数据成功", specs);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Spec> getSpec(@PathVariable("id") Integer id) {
        Spec spec = specService.getSpecById(id);
        Result<Spec> result = null;
        if (spec != null) {
            result = new Result<>(true, StatusCode.OK, "查询规格数据成功", spec);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到规格数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getSpec(@RequestBody Spec spec) {
        specService.addSpec(spec);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入规格成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param spec
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefySpec(@PathVariable("id") Integer id, @RequestBody Spec spec) {
        spec.setId(id);
        specService.modefySpec(spec);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改规格成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefySpec(@PathVariable("id") Integer id) {
        specService.deleteSpecById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除规格成功", null);
        return result;
    }

    /**
     * 根据规格名和首字母搜索
     *
     * @param spec
     * @return
     */
    @PostMapping("/search")
    public Result<List<Spec>> searchSpec(@RequestBody Spec spec) {
        List<Spec> specs = specService.searchSpec(spec);
        Result<List<Spec>> result = new Result<>(true, StatusCode.OK, "根据条件查询规格成功", specs);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Spec>> searchSpecPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Spec> specPageInfo = specService.getAllSpecPaged(page, size);
        Result<PageInfo<Spec>> result = new Result<>(true, StatusCode.OK, "根据条件查询规格成功", specPageInfo);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Spec>> searchSpecPaged(@RequestBody Spec spec,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Spec> specPageInfo = specService.searchSpecPaged(spec, page, size);
        Result<PageInfo<Spec>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询规格成功", specPageInfo);
        return result;
    }

    /**
     * 根据分类查询规格
     *
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Spec>> getSpecByCategoryId(@PathVariable("id") Integer id) {
        List<Spec> specs = specService.getSpecByCategoryId(id);
        Result<List<Spec>> result = new Result<>(true, StatusCode.OK, "根据分类查询规格成功", specs);
        return result;
    }
}

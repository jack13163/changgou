package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Para;
import jack.changgou.service.ParaService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/para")
@CrossOrigin // 解决跨域问题
public class ParaController {
    @Autowired
    ParaService paraService;

    @GetMapping
    public Result<List<Para>> allPara() {
        List<Para> paras = paraService.getAllPara();
        Result<List<Para>> result = new Result<>(true, StatusCode.OK, "查询参数数据成功", paras);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Para> getPara(@PathVariable("id") Integer id) {
        Para para = paraService.getParaById(id);
        Result<Para> result = null;
        if (para != null) {
            result = new Result<>(true, StatusCode.OK, "查询参数数据成功", para);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到参数数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getPara(@RequestBody Para para) {
        paraService.addPara(para);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入参数成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param para
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyPara(@PathVariable("id") Integer id, @RequestBody Para para) {
        para.setId(id);
        paraService.modefyPara(para);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改参数成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyPara(@PathVariable("id") Integer id) {
        paraService.deleteParaById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除参数成功", null);
        return result;
    }

    /**
     * 根据参数名和模板Id搜索
     *
     * @param para
     * @return
     */
    @PostMapping("/search")
    public Result<List<Para>> searchSpec(@RequestBody Para para) {
        List<Para> brands = paraService.searchPara(para);
        Result<List<Para>> result = new Result<>(true, StatusCode.OK, "根据条件查询参数成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Para>> searchSpecPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Para> paraPageInfo = paraService.getAllParaPaged(page, size);
        Result<PageInfo<Para>> result = new Result<>(true, StatusCode.OK, "根据条件查询参数成功", paraPageInfo);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Para>> searchSpecPaged(@RequestBody Para para,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Para> paraPageInfo = paraService.searchParaPaged(para, page, size);
        Result<PageInfo<Para>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询参数成功", paraPageInfo);
        return result;
    }

    /**
     * 根据分类查询参数
     *
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Para>> getParaByCategoryId(@PathVariable("id") Integer id) {
        List<Para> specs = paraService.getParasByCategoryId(id);
        Result<List<Para>> result = new Result<>(true, StatusCode.OK, "根据分类查询参数成功", specs);
        return result;
    }
}

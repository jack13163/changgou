package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Template;
import jack.changgou.service.TemplateService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import jack.changgou.vo.TemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/template")
@CrossOrigin // 解决跨域问题
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @GetMapping
    public Result<List<Template>> allTemplate() {
        List<Template> templates = templateService.getAllTemplate();
        Result<List<Template>> result = new Result<>(true, StatusCode.OK, "查询模板数据成功", templates);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Template> getTemplate(@PathVariable("id") Integer id) {
        Template template = templateService.getTemplateById(id);
        Result<Template> result = null;
        if (template != null) {
            result = new Result<>(true, StatusCode.OK, "查询模板数据成功", template);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到模板数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> addTemplate(@RequestBody TemplateVo templateVo) {
        templateService.saveTemplate(templateVo);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入模板成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param template
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyTemplate(@PathVariable("id") Integer id, @RequestBody Template template) {
        template.setId(id);
        templateService.modefyTemplate(template);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改模板成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyTemplate(@PathVariable("id") Integer id) {
        templateService.deleteTemplateById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除模板成功", null);
        return result;
    }

    /**
     * 根据模板名和首字母搜索
     *
     * @param template
     * @return
     */
    @PostMapping("/search")
    public Result<List<Template>> searchBrand(@RequestBody Template template) {
        List<Template> templates = templateService.searchTemplate(template);
        Result<List<Template>> result = new Result<>(true, StatusCode.OK, "根据条件查询模板成功", templates);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<TemplateVo>> searchTemplatePaged(@PathVariable("page") Integer page,
                                                            @PathVariable("size") Integer size) {
        PageInfo<TemplateVo> templatePageInfo = templateService.getAllTemplateVo(page, size);
        Result<PageInfo<TemplateVo>> result = new Result<>(true, StatusCode.OK, "根据条件查询模板成功", templatePageInfo);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Template>> searchBrandPaged(@RequestBody Template template,
                                                       @PathVariable("page") Integer page,
                                                       @PathVariable("size") Integer size) {
        PageInfo<Template> templatePageInfo = templateService.searchTemplatePaged(template, page, size);
        Result<PageInfo<Template>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询模板成功", templatePageInfo);
        return result;
    }
}

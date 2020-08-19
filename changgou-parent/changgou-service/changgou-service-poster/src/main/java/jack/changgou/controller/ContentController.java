package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.poster.pojo.Content;
import jack.changgou.service.ContentService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/poster")
@RestController
public class ContentController {

    @Autowired
    ContentService contentService;

    @GetMapping
    public Result<List<Content>> allContent() {
        List<Content> contents = contentService.getAllContent();
        Result<List<Content>> result = new Result<>(true, StatusCode.OK, "查询广告数据成功", contents);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Content> getContent(@PathVariable("id") Integer id) {
        Content content = contentService.getContentById(id);
        Result<Content> result = null;
        if (content != null) {
            result = new Result<>(true, StatusCode.OK, "查询指定广告数据成功", content);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前广告数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getContent(@RequestBody Content content) {
        contentService.addContent(content);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入广告数据信息成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param content
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyContent(@PathVariable("id") Integer id, @RequestBody Content content) {
        content.setId(id);
        contentService.modefyContent(content);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改广告数据信息成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyContent(@PathVariable("id") Integer id) {
        contentService.deleteContentById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除广告数据成功", null);
        return result;
    }

    /**
     * 根据广告数据名和首字母搜索
     *
     * @param content
     * @return
     */
    @PostMapping("/search")
    public Result<List<Content>> searchContent(@RequestBody Content content) {
        List<Content> brands = contentService.searchContent(content);
        Result<List<Content>> result = new Result<>(true, StatusCode.OK, "根据条件查询广告数据成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/{page}/{size}")
    public Result<PageInfo<Content>> searchContentPaged(@PathVariable("page") Integer page,
                                                        @PathVariable("size") Integer size) {
        PageInfo<Content> contents = contentService.getAllContentPaged(page, size);
        Result<PageInfo<Content>> result = new Result<>(true, StatusCode.OK, "根据条件查询广告数据成功", contents);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Content>> searchContentPaged(@RequestBody Content content,
                                                        @PathVariable("page") Integer page,
                                                        @PathVariable("size") Integer size) {
        PageInfo<Content> brands = contentService.searchContentPaged(content, page, size);
        Result<PageInfo<Content>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询广告数据成功", brands);
        return result;
    }

}

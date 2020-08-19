package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.poster.pojo.ContentCategory;
import jack.changgou.service.ContentCategoryService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/poster_category")
@RestController
public class ContentCategoryController {

    @Autowired
    ContentCategoryService contentCategoryService;

    @GetMapping
    public Result<List<ContentCategory>> allContentCategory() {
        List<ContentCategory> albums = contentCategoryService.getAllContentCategory();
        Result<List<ContentCategory>> result = new Result<>(true, StatusCode.OK, "查询广告分类数据成功", albums);
        return result;
    }

    @GetMapping("/{id}")
    public Result<ContentCategory> getContentCategory(@PathVariable("id") Integer id) {
        ContentCategory album = contentCategoryService.getContentCategoryById(id);
        Result<ContentCategory> result = null;
        if (album != null) {
            result = new Result<>(true, StatusCode.OK, "查询指定广告分类数据成功", album);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前广告分类数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getContentCategory(@RequestBody ContentCategory album) {
        contentCategoryService.addContentCategory(album);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入广告分类数据信息成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param album
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyContentCategory(@PathVariable("id") Long id, @RequestBody ContentCategory album) {
        album.setId(id);
        contentCategoryService.modefyContentCategory(album);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改广告分类数据信息成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyContentCategory(@PathVariable("id") Integer id) {
        contentCategoryService.deleteContentCategoryById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除广告分类数据成功", null);
        return result;
    }

    /**
     * 根据广告分类数据名和首字母搜索
     *
     * @param album
     * @return
     */
    @PostMapping("/search")
    public Result<List<ContentCategory>> searchContentCategory(@RequestBody ContentCategory album) {
        List<ContentCategory> brands = contentCategoryService.searchContentCategory(album);
        Result<List<ContentCategory>> result = new Result<>(true, StatusCode.OK, "根据条件查询广告分类数据成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/{page}/{size}")
    public Result<PageInfo<ContentCategory>> searchContentCategoryPaged(@PathVariable("page") Integer page,
                                                        @PathVariable("size") Integer size) {
        PageInfo<ContentCategory> albums = contentCategoryService.getAllContentCategoryPaged(page, size);
        Result<PageInfo<ContentCategory>> result = new Result<>(true, StatusCode.OK, "根据条件查询广告分类数据成功", albums);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<ContentCategory>> searchContentCategoryPaged(@RequestBody ContentCategory album,
                                                        @PathVariable("page") Integer page,
                                                        @PathVariable("size") Integer size) {
        PageInfo<ContentCategory> brands = contentCategoryService.searchContentCategoryPaged(album, page, size);
        Result<PageInfo<ContentCategory>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询广告分类数据成功", brands);
        return result;
    }

}

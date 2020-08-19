package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Category;
import jack.changgou.service.CategoryService;
import jack.changgou.vo.MenuVo;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin // 解决跨域问题
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> allCategory() {
        List<Category> categories = categoryService.getAllCategory();
        Result<List<Category>> result = new Result<>(true, StatusCode.OK, "查询分类数据成功", categories);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Category> getCategory(@PathVariable("id") Integer id) {
        Category category = categoryService.getCategoryById(id);
        Result<Category> result = null;
        if (category != null) {
            result = new Result<>(true, StatusCode.OK, "查询分类数据成功", category);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到分类数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入分类成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param category
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
        category.setId(id);
        categoryService.modefyCategory(category);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改分类成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategoryById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除分类成功", null);
        return result;
    }

    /**
     * 根据分类名和模板Id搜索
     *
     * @param category
     * @return
     */
    @PostMapping("/search")
    public Result<List<Category>> searchCategory(@RequestBody Category category) {
        List<Category> brands = categoryService.searchCategory(category);
        Result<List<Category>> result = new Result<>(true, StatusCode.OK, "根据条件查询分类成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Category>> searchCategoryPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Category> categoryPageInfo = categoryService.getAllCategoryPaged(page, size);
        Result<PageInfo<Category>> result = new Result<>(true, StatusCode.OK, "根据条件查询分类成功", categoryPageInfo);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Category>> searchCategoryPaged(@RequestBody Category category,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Category> brands = categoryService.searchCategoryPaged(category, page, size);
        Result<PageInfo<Category>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询分类成功", brands);
        return result;
    }

    @GetMapping("/list/{pid}")
    public Result<List<Category>> getCategoryByParentId(@PathVariable("pid") Integer pid) {
        List<Category> category = categoryService.getChildCategoryByParentCategoryId(pid);
        Result<List<Category>> result = null;
        if (category != null) {
            result = new Result<>(true, StatusCode.OK, "查询子分类数据成功", category);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到子分类数据", null);
        }
        return result;
    }

    /**
     * 从数据库中加载分类菜单节点
     * @return
     */
    @GetMapping("/menu")
    public Result<List<MenuVo>> getThreeNode() {
        List<MenuVo> menus = getTreeDataRecursion(0);
        Result<List<MenuVo>> result = new Result<>(true, StatusCode.OK, "查询菜单数据成功", menus);
        return result;
    }

    /**
     * 递归方法，调用此方法时候，将 0 传入，0代表一级菜单
     * @param id
     * @return
     */
    private List<MenuVo> getTreeDataRecursion(Integer id) {
        // 获取所有的子级
        List<Category> categories = categoryService.getChildCategoryByParentCategoryId(id);

        // 判断子级是否还有子级
        if (categories == null || categories.size() < 1) {
            // 如果没有子级则返回空
            return null;
        }

        List<MenuVo> result = new ArrayList<>();
        // 循环获得的子级，将子级放入父级中
        for (Category category : categories) {
            // 父亲节点
            MenuVo parent = new MenuVo(category.getId(), category.getName(), getTreeDataRecursion(category.getId()));
            // 将父亲节点放入到结果中
            result.add(parent);
        }
        // 返回菜单集合
        return result;
    }
}

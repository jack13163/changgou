package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.CategoryBrand;
import jack.changgou.service.CategoryBrandService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category_brand")
@CrossOrigin // 解决跨域问题
public class CategoryBrandController {
    @Autowired
    CategoryBrandService categoryBrandService;

    @GetMapping
    public Result<List<CategoryBrand>> allCategoryBrand() {
        List<CategoryBrand> categoryBrands = categoryBrandService.getAllCategoryBrand();
        Result<List<CategoryBrand>> result = new Result<>(true, StatusCode.OK, "查询品牌-分类数据成功", categoryBrands);
        return result;
    }

    @GetMapping("/b/{id}")
    public Result<CategoryBrand> getCategoryBrandByBrandId(@PathVariable("id") Integer brandId) {
        List<CategoryBrand> categoryBrands = categoryBrandService.getCategoryIdsByBrandId(brandId);
        Result<CategoryBrand> result = null;
        if (categoryBrands != null) {
            result = new Result<>(true, StatusCode.OK, "查询当前分类的品牌数据成功", categoryBrands);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前分类的品牌数据", null);
        }
        return result;
    }

    @GetMapping("/c/{id}")
    public Result<CategoryBrand> getCategoryBrandByCategoryId(@PathVariable("id") Integer categoryId) {
        List<CategoryBrand> categoryBrands = categoryBrandService.getBrandIdsByCategoryId(categoryId);
        Result<CategoryBrand> result = null;
        if (categoryBrands != null) {
            result = new Result<>(true, StatusCode.OK, "查询当前品牌的分类数据成功", categoryBrands);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前品牌的分类数据", null);
        }
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyCategoryBrand(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.modefyCategoryBrand(categoryBrand);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改品牌-分类数据成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteCategoryBrand(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.deleteCategoryBrand(categoryBrand);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除品牌-分类数据成功", null);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/{page}/{size}")
    public Result<PageInfo<CategoryBrand>> searchCategoryBrandPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<CategoryBrand> categoryBrandPageInfo = categoryBrandService.getAllCategoryBrandPaged(page, size);
        Result<PageInfo<CategoryBrand>> result = new Result<>(true, StatusCode.OK, "根据条件查询品牌成功", categoryBrandPageInfo);
        return result;
    }

}

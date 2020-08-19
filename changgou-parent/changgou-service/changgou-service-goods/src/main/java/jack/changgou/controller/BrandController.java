package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Brand;
import jack.changgou.service.BrandService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin // 解决跨域问题
public class BrandController {
    @Autowired
    BrandService brandService;

    @GetMapping
    public Result<List<Brand>> allBrand() {
        List<Brand> brands = brandService.getAllBrand();
        Result<List<Brand>> result = new Result<>(true, StatusCode.OK, "查询品牌数据成功", brands);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Brand> getBrand(@PathVariable("id") Integer id) {
        Brand brand = brandService.getBrandById(id);
        Result<Brand> result = null;
        if (brand != null) {
            result = new Result<>(true, StatusCode.OK, "查询品牌数据成功", brand);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到品牌数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getBrand(@RequestBody Brand brand) {
        brandService.addBrand(brand);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入品牌成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param brand
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyBrand(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId(id);
        brandService.modefyBrand(brand);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改品牌成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyBrand(@PathVariable("id") Integer id) {
        brandService.deleteBrandById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除品牌成功", null);
        return result;
    }

    /**
     * 根据品牌名和首字母搜索
     *
     * @param brand
     * @return
     */
    @PostMapping("/search")
    public Result<List<Brand>> searchBrand(@RequestBody Brand brand) {
        List<Brand> brands = brandService.searchBrand(brand);
        Result<List<Brand>> result = new Result<>(true, StatusCode.OK, "根据条件查询品牌成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> searchBrandPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Brand> brands = brandService.getAllBrandPaged(page, size);
        Result<PageInfo<Brand>> result = new Result<>(true, StatusCode.OK, "根据条件查询品牌成功", brands);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> searchBrandPaged(@RequestBody Brand brand,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Brand> brands = brandService.searchBrandPaged(brand, page, size);
        Result<PageInfo<Brand>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询品牌成功", brands);
        return result;
    }


    @GetMapping("/category/{id}")
    public Result<List<Brand>> getBrandsByCategoryId(@PathVariable("id") Integer id) {
        List<Brand> category = brandService.getBrandsByCategoryId(id);
        Result<List<Brand>> result = null;
        if (category != null) {
            result = new Result<>(true, StatusCode.OK, "查询某一分类下的所有品牌成功", category);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前分类下的品牌数据", null);
        }
        return result;
    }
}

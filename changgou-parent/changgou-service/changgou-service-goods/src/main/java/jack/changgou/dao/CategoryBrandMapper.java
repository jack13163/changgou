package jack.changgou.dao;

import jack.changgou.goods.pojo.CategoryBrand;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CategoryBrandMapper extends Mapper<CategoryBrand> {
}

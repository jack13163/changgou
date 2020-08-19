package jack.changgou.dao;

import jack.changgou.goods.pojo.Category;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CategoryMapper extends Mapper<Category> {
}

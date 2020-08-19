package jack.changgou.dao;

import jack.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 继承自通用mapper
 * 添加数据：mapper.insert()
 * 修改数据：mapper.update(T)
 * 查询数据：
 *  根据ID查询：mapper.selectByPrimaryKey(Id)
 *  根据条件查询：mapper.select(T)
 */
public interface BrandMapper extends Mapper<Brand> {
    @Select("select brand.* from tb_brand brand where id in (select tcb.brand_id from tb_category_brand tcb where category_id = #{id})")
    List<Brand> getBrandsByByCategoryId(@Param("id") Integer id);
}

package jack.changgou.dao;

import jack.changgou.goods.pojo.Spec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface SpecMapper extends Mapper<Spec> {
    @Select("select spec.* from tb_spec spec where template_id in (select tc.template_id from tb_category tc where id = #{cid})")
    List<Spec> getSpecByCategoryId(@Param("cid") Integer cid);
}

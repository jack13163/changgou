package jack.changgou.dao;

import jack.changgou.goods.pojo.Template;
import jack.changgou.vo.TemplateVo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface TemplateMapper extends Mapper<Template> {
}

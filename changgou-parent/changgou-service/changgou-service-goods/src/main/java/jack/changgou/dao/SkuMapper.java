package jack.changgou.dao;

import jack.changgou.goods.pojo.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku> {
    /**
     * 减少商品库存
     * @param id
     * @param num
     * @return
     */
    @Update("update tb_sku set num=num-#{num} where id=#{id} and num>=#{num}")
    int decrCount(@Param("id") Long id, @Param("num") Integer num);
}

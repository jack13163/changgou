package jack.changgou.seckill.timer;

import jack.changgou.seckill.dao.SeckillGoodsMapper;
import jack.changgou.seckill.pojo.SeckillGoods;
import jack.changgou.vo.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 定时将数据库中的秒杀商品推送到Redis中
 */
@Component
public class SecKillGoodsPushTask {

    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 每30秒执行一次
     * 秒、分、时、日、月、周、年
     * 日和周可以使用？，其他的不可以使用；
     * 年可以省略
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void loadGoodsPushRedis() {
        System.out.println("正在将MySQL中的秒杀商品信息同步到Redis中...");
        // 计算时间菜单
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (int i = 0; i < dateMenus.size(); i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            String group = "SeckillGoods_" + simpleDateFormat.format(dateMenus.get(i));

            // 查询商品信息，已审核（status=1），库存大于0（stockCount>0），时间符合（startTime，endTime）
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status", "1");
            // 时间比较大小
            criteria.andGreaterThanOrEqualTo("startTime", dateMenus.get(i));
            criteria.andLessThanOrEqualTo("endTime", DateUtil.addDateHour(dateMenus.get(i), 2));
            // 排除已经存储的商品
            Set keys = redisTemplate.boundHashOps(group).keys();
            if (keys != null && !keys.isEmpty()) {
                criteria.andNotIn("id", keys);
            }

            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);

            // 存入到redis中
            seckillGoods.forEach(goods -> {
                System.out.println("商品[" + goods.getId() + "]存入Redis缓存中");
                redisTemplate.boundHashOps(group).put(goods.getId().toString(), goods);// 注意redis中区分String和Double，这里统一为String
            });
        }
    }
}

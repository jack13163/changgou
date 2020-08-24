package jack.changgou.user.feign;

import jack.changgou.user.pojo.User;
import jack.changgou.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("user")// 指定服务名字
@RequestMapping("/user")// 指定调用方法
public interface UserFeign {
    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<User> findById(@PathVariable String id);


    /**
     * 增加用户积分
     * @param points
     */
    @GetMapping("/points/add")
    Result addPoints(Integer points);
}

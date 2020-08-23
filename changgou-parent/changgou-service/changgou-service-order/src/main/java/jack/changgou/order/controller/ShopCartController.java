package jack.changgou.order.controller;

import jack.changgou.order.pojo.OrderItem;
import jack.changgou.order.service.ShopCartService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import jack.changgou.vo.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 购物车操作
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class ShopCartController {

    @Autowired
    ShopCartService shopCartService;

    /**
     * 加入购物车
     *
     * @param id  商品id
     * @param num 商品数量
     * @return
     */
    @GetMapping("/add")
    public Result add(Long id, Integer num) throws IOException {
        Map<String, String> map = TokenDecode.getUserInfo();

        shopCartService.add(id, num, map.get("username"));
        return new Result(true, StatusCode.OK, "添加购物车成功");
    }



    /**
     * 查询购物车列表
     */
    @GetMapping("/list")
    public Result list() throws IOException {
        // SpringSecurity获取用户信息：先获取认证详情，并从中获取令牌，解析令牌后得到用户信息
        Map<String, String> map = TokenDecode.getUserInfo();

        String username = map.get("username");
        List<OrderItem> orderItemList = shopCartService.getAll(username);
        return new Result(true, StatusCode.OK, "查询购物车成功", orderItemList);
    }
}

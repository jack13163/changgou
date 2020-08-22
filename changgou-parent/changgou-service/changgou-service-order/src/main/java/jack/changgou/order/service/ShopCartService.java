package jack.changgou.order.service;

import jack.changgou.order.pojo.OrderItem;

import java.util.List;

public interface ShopCartService {
    // 加入购物车，将其存入redis中
    void add(Long id, Integer num, String username);

    // 查询购物车列表
    List<OrderItem> getAll(String username);
}

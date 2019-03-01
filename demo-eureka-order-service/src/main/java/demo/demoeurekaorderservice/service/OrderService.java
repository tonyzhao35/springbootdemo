package demo.demoeurekaorderservice.service;

import demo.demoeurekaorderservice.domain.Order;

/**
 * 订单业务类
 */
public interface OrderService {

    /**
     * 下单接口
     * @param userId
     * @param productId
     * @return
     */
    Order save(String userId, String productId);
}

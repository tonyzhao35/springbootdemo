package demo.demoeurekaorderservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import demo.demoeurekaorderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id") String userId, @RequestParam("product_id") String productId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("successed", "true");
        ret.put("data", orderService.save(userId, productId));
        return ret;
    }

    @RequestMapping("save_product_client")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object saveByProductClient(@RequestParam("user_id") String userId, @RequestParam("product_id") String productId) {
        return orderService.saveByProductClient(userId, productId);
    }

    private Object saveOrderFail(String userId, String productId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("successed", "false");
        ret.put("message", "调用失败");
        return ret;
    }
}

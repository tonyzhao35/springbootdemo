package demo.demoeurekaorderservice.controller;

import com.netflix.discovery.converters.Auto;
import demo.demoeurekaorderservice.service.OrderService;
import demo.demoeurekaorderservice.service.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("save")
    public Object save(@RequestParam("user_id") String userId,@RequestParam("product_id") String productId){
        return orderService.save(userId, productId);
    }

    @RequestMapping("save_product_client")
    public Object saveByProductClient(@RequestParam("user_id") String userId,@RequestParam("product_id") String productId){
        return orderService.saveByProductClient(userId, productId);
    }
}

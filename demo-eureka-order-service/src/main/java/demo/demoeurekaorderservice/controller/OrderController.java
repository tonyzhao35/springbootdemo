package demo.demoeurekaorderservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import demo.demoeurekaorderservice.service.OrderService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id") String userId, @RequestParam("product_id") String productId, HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("successed", "true");
        ret.put("data", orderService.save(userId, productId));
        return ret;
    }

    @RequestMapping("save_product_client")
//    @HystrixCommand(fallbackMethod = "saveOrderFail", commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
//    })
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object saveByProductClient(@RequestParam("user_id") String userId, @RequestParam("product_id") String productId, HttpServletRequest request) {
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");
        System.out.println("token=" + token);
        System.out.println("cookie=" + cookie);

        return orderService.saveByProductClient(userId, productId);
    }

    private Object saveOrderFail(String userId, String productId, HttpServletRequest request) {
        //监控报警
        String saveOrderKey = "save-order";
        String value = redisTemplate.opsForValue().get(saveOrderKey);
        new Thread(() -> {
            if (StringUtils.isEmpty(value)) {
                System.out.println("用户下单失败，请立即查找原因，IP地址为：" + request.getRemoteAddr());
                redisTemplate.opsForValue().set(saveOrderKey, "message", 20, TimeUnit.SECONDS);
                //发送一个http请求，调用短信服务
            } else {
                System.out.println("已发送消息，20秒内不再重发");
            }
        }).start();

        Map<String, Object> ret = new HashMap<>();
        ret.put("successed", "false");
        ret.put("message", "调用失败");
        return ret;
    }
}

package demo.demoeurekaorderservice.fallback;

import demo.demoeurekaorderservice.service.ProductClient;
import org.springframework.stereotype.Component;

/**
 * 针对商品服务，做降级处理
 */
@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public String findById(int id) {
        System.out.println("feign 调用product-service异常");
        return null;
    }
}

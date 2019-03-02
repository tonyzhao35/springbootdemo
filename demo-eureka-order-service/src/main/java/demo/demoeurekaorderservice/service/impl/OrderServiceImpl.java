package demo.demoeurekaorderservice.service.impl;

import demo.demoeurekaorderservice.domain.Order;
import demo.demoeurekaorderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private LoadBalancerClient loadBalancer;

    @Override
    public Order save(String userId, String productId) {

        // 调用方式一
        // 获取商品详情（使用ribbon）
        Map<String, Object> obj = restTemplate.getForObject("http://product-service/api/v1/product/find?id=" + productId, Map.class);

        // 调用方式二
//        ServiceInstance instance = loadBalancer.choose("product-service");
//        String uri = String.format("http://%s:%s/api/v1/product/find?id=" + productId,instance.getHost(),instance.getPort());
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> obj = restTemplate.getForObject(uri, Map.class);

        // 调用用户服务，主要是获取用户名称，用户级别或者积分信息

        System.out.println(obj);
        if(obj != null){
            Order order = new Order();
            order.setCreateTime(new Date());
            order.setUserId(userId);
            order.setTradeNo(UUID.randomUUID().toString());
//            order.setProductId(getAttributeValue(obj,"id"));
//            order.setProductName(getAttributeValue(obj,"name"));
//            order.setPrice(Integer.parseInt(getAttributeValue(obj,"price")));
            order.setProductId(obj.get("id").toString());
            order.setProductName(obj.get("name").toString());
            order.setPrice(Integer.parseInt(obj.get("price").toString()));
            return order;
        }
        return null;
    }

    /**
     * 得到属性值
     * @param obj
     */
    private String getAttributeValue(Object obj, String attr){
        LinkedHashMap<Object, Object> objs = (LinkedHashMap<Object, Object>)obj;
        for (Map.Entry<Object, Object> entry : objs.entrySet()){
            //获取属性`
            String key = entry.getKey().toString();
            //一个个赋值
            if(attr.equals(key)){
                //获取属性值
                String value = entry.getValue().toString();
                return value==null?null:value.toString();
            }
        }
        return null;
    }
}

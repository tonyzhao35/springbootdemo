package demo.demoeurekaproductservice.controller;

import com.netflix.discovery.converters.Auto;
import demo.demoeurekaproductservice.domain.Product;
import demo.demoeurekaproductservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品列表
     * @return
     */
    @RequestMapping("list")
    public Object list(){
        return productService.listProduct();
    }

    /**
     * 根据id查找商品详情
     * @param id
     * @return
     */
    @RequestMapping("find")
    public Object findById(@RequestParam("id") int id){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("findById");
        Product product = productService.findById(id);
        Product result = new Product();
        if(product != null){
            BeanUtils.copyProperties(product, result);
            result.setName(result.getName() + " port from = " + port);
            return result;
        }else{
            return null;
        }
    }
}

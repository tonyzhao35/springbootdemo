package demo.demoeurekaproductservice.service.impl;

import demo.demoeurekaproductservice.domain.Product;
import demo.demoeurekaproductservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Map<Integer,Product> productMap = new HashMap<>();

    static {
        Product p1 = new Product(1,"iphonexs",10000,100);
        Product p2 = new Product(2,"冰箱",5234,23);
        Product p3 = new Product(3,"洗衣机",3234,34);
        Product p4 = new Product(4,"电话",598,56);
        Product p5 = new Product(5,"椅子",150,21);
        Product p6 = new Product(6,"空调",2500,67);
        Product p7 = new Product(7,"耳机",2600,120);

        productMap.put(p1.getId(),p1);
        productMap.put(p2.getId(),p2);
        productMap.put(p3.getId(),p3);
        productMap.put(p4.getId(),p4);
        productMap.put(p5.getId(),p5);
        productMap.put(p6.getId(),p6);
        productMap.put(p7.getId(),p7);
    }
    @Override
    public List<Product> listProduct() {
        Collection<Product> collection = productMap.values();
        List<Product> list = new ArrayList<>(collection);
        return list;
    }

    @Override
    public Product findById(int id) {
        return productMap.get(id);
    }
}

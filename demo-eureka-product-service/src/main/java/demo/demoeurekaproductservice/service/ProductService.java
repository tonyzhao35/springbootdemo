package demo.demoeurekaproductservice.service;

import demo.demoeurekaproductservice.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProduct();

    Product findById(int Id);
}

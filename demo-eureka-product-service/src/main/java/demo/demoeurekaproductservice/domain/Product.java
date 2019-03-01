package demo.demoeurekaproductservice.domain;

import java.io.Serializable;

public class Product implements Serializable {

    public Product(){}

    public Product(int id, String name, int price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * ID
     */
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private int price;

    /**
     * 库存
     */
    private int stock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

package demo.demoapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class DemoApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiGatewayApplication.class, args);
    }

}

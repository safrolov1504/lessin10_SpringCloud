package com.geekbrains.home;

import com.geekbrains.home.productsUtils.ProductRepository;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingControllerImpl implements GreetingController {
    @Qualifier("eurekaClient")
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("product-server")
    private String appName;

    @Autowired
    private ProductRepository productRepository;

//    @Value("${userValue}")
//    private String username;

    @Override
    @RequestMapping("/greeting")
    public String greeting() {
        return String.format("Hello from '%s'!", eurekaClient.getApplication(appName).getName());
    }


    @GetMapping("/products")
    public List<DtoProduct> allProducts(){
        return productRepository.findAllBy();
    }

    @GetMapping("/parametrized/{id}")
    public String parametrized(@PathVariable(value = "id") String id) {
        return "echo: " + id;
    }
}

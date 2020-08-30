package com.geekbrains.home;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class GreetingControllerImpl implements GreetingController {
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Qualifier("eurekaClient")
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Value("view-server")
    private String appName;


//    @Value("${userValue}")
//    private String username;

    @Override
    @RequestMapping("/greeting")
    public String greeting() {
        return String.format("Hello from '%s'!", eurekaClient.getApplication(appName).getName());
    }


    @GetMapping("/products")
    public String allProducts(Model model){
        //ResponseEntity restExchange = restTemplate.exchange("http://localhost:9872/products", HttpMethod.GET, null, List.class);
        eurekaClient.getNextServerFromEureka("product-server",false).getPort();
        String urlProduct = eurekaClient.getNextServerFromEureka("product-server",false).getHomePageUrl();
        System.out.println(urlProduct);
        int portProduct = eurekaClient.getNextServerFromEureka("product-server",false).getPort();
        ResponseEntity restExchange = restTemplate.exchange("http://localhost:"+portProduct+"/products", HttpMethod.GET, null, List.class);
        List<DtoProduct> productDto = (List<DtoProduct>)restExchange.getBody();
        model.addAttribute("products", productDto);
        return "all_products";
    }

    @GetMapping("/parametrized/{id}")
    public String parametrized(@PathVariable(value = "id") String id) {
        return "echo: " + id;
    }
}

package springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring Boot HelloWorld 案例
 *
 * Created by bysocket on 16/4/26.
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public Object sayHello() {
        Map<String,Object> r = new HashMap<>();
        r.put("name","hc");
        return r;
    }

    public Object test(){
        return "";
    }
}

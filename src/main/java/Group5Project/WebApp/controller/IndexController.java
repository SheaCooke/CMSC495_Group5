package Group5Project.WebApp.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home (Map<String, Object> model) {
        model.put("message", "test");
        return "index";
    }
    @GetMapping("thyme")
    public String greeting (Map<String, Object> model) {
        model.put("message", "test");
        return "thyme";
    }
}

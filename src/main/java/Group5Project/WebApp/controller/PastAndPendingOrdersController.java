package Group5Project.WebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;

@Controller
public class PastAndPendingOrdersController {

    @GetMapping("PastAndPendingOrders")
    public String PastAndPendingOrders (Map<String, Object> model) {
        model.put("CurrentPendingOrders", CurrentPendingOrders);
        return "PastAndPendingOrders";
    }
}

package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    //Other code

//    @PostMapping("/login_success_handler")
//    public String loginSuccessHandler() {
//        //perform audit action
//        return "/";
//    }
//
//    @PostMapping("/login_failure_handler")
//    public String loginFailureHandler() {
//        //perform audit action
//        return "Login";
//    }

    @GetMapping("/Login")
    public String login (Model model) {

        model.addAttribute("dto", new UserDto());

        return "Login";
    }

    @RequestMapping(value="/Logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:Login";
    }
}
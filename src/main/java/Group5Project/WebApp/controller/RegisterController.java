package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.UserDto;
import Group5Project.WebApp.model.Order;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.ArrayList;

import static Group5Project.WebApp.Data.Authorities.grantedAuthorities;
import static Group5Project.WebApp.Data.Cart.ItemsInCart;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;

@NoArgsConstructor
@Controller
public class RegisterController {

    @Autowired
   private InMemoryUserDetailsManager inMemoryUserDetailsManager;
//
//
//    public RegisterController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
//        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
//    }




    @PostMapping("/register")
    public String Register(Model model, UserDto dto) {

        //make sure no one is currently logged in


          //  grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

            //UserDetails user = new User("test", "test", grantedAuthorities);

//            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);

              //  inMemoryUserDetailsManager.createUser();

        User.UserBuilder user = User.withDefaultPasswordEncoder();


        inMemoryUserDetailsManager.createUser(user.username(dto.getEmail()).password(dto.getPassword()).roles("USER").build());

        return "redirect:Login";
    }

}

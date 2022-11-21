package Group5Project.WebApp.Data;

import Group5Project.WebApp.model.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;


import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;

public class Authorities {

    public static List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
}

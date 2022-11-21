package Group5Project.WebApp.controller;
import Group5Project.WebApp.Data.Cart;
import Group5Project.WebApp.Data.CurrentUser;
import Group5Project.WebApp.Data.Menu;
import Group5Project.WebApp.Data.UserDto;
import Group5Project.WebApp.model.Item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static Group5Project.WebApp.Data.Cart.ItemsInCart;
import static Group5Project.WebApp.Data.Cart.addItem;
import static Group5Project.WebApp.Data.Menu.MenuItems;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home (Map<String, Object> model) {

        //for testing, replace with query to sql

        //MenuItems.clear();

        if (MenuItems.size() == 0)
        {
            Item item1 = new Item("item1", 1, 10);

            Item item2 = new Item("item2", 1, 15);

            MenuItems.add(item1);
            MenuItems.add(item2);
        }
        //create new current user object


        model.put("MenuItems", MenuItems);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        CurrentUser.currentUserName = username;

        model.put("un", username);

        model.put("role", "USER");

        if (hasRole("ROLE_ADMIN"))
        {
            model.put("role", "ADMIN");

            return "admin";
        }

        return "index";
    }

    @PostMapping("/AddToCart/{ID}")
    public String AddToCart(@PathVariable final UUID ID) {

        Item itemToAdd = MenuItems.stream().filter(i -> i.ID.equals(ID)).findFirst().get();

        addItem(itemToAdd);

        return "redirect:/";
    }

    @PostMapping("/RemoveFromCart/{ID}")
    public String DecramentItem(@PathVariable final UUID ID) {

        boolean removeItem = false;

        for (var i : ItemsInCart)
        {
            if (i.ID.equals(ID))
            {
                if (i.Quantity <= 1)
                {

                    removeItem = true;
                    break;
                }
                else
                {
                    i.Quantity--;
                }

            }
        }

        if (removeItem)
        {
            RemoveItem(ID);
        }

        return "redirect:/Cart";
    }

    @PostMapping("/IncramentItem/{ID}")
    public String IncramentItem(@PathVariable final UUID ID) {

        for (var i : ItemsInCart)
        {
            if (i.ID.equals(ID))
            {
                i.Quantity++;
            }
        }

        return "redirect:/Cart";
    }

    public static boolean hasRole (String roleName)
    {
//        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(roleName));

        return hasUserRole;
    }

    public static void RemoveItem(UUID ID)
    {
        ItemsInCart.removeIf(j -> j.ID.equals(ID));
    }



}

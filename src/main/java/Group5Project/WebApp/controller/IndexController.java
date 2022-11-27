package Group5Project.WebApp.controller;
import Group5Project.WebApp.Data.*;
import Group5Project.WebApp.model.Item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


import static Group5Project.WebApp.Data.CompletedOrders.newlyCompletedOrders;
import static Group5Project.WebApp.Data.CurrentUser.*;
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

        model.put("MenuItems", MenuItems);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        CurrentUser.currentUserName = username;


        //CurrentUser.currentView = "home";

        if(!CartExists(username))
        {
            Cart cart = new Cart(username);

            CartCollection.AllCarts.add(cart);
        }

        if (!UserInformationExists(username))
        {
            CurrentUserInformation currentUserInformation = new CurrentUserInformation(username, 0);

            currentUserInformationList.add(currentUserInformation);
        }

        model.put("NewlyCompletedOrders", GetNotificationsByUserName(CurrentUser.currentUserName));

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

        Item itemToClone = MenuItems.stream().filter(i -> i.ID.equals(ID)).findFirst().get();

        Item itemToAdd = new Item(itemToClone.ItemName, 1, itemToClone.Price);

        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        cartToModoify.addItem(itemToAdd);

        return "redirect:/";
    }

    @PostMapping("/RemoveFromCart/{ID}")
    public String DecramentItem(@PathVariable final UUID ID) {

        boolean removeItem = false;

        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        for (var i : cartToModoify.ItemsInCart)
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

        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        for (var i : cartToModoify.ItemsInCart)
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
        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        cartToModoify.ItemsInCart.removeIf(j -> j.ID.equals(ID));
    }

    public static Cart GetCartByUserName(String username)
    {
        if (CartExists(username))
        {
            return CartCollection.AllCarts.stream().filter(i -> i.UserName.equals(username)).findFirst().get();
        }
        else
        {
            return new Cart(CurrentUser.currentUserName);
        }

    }

    public static boolean CartExists(String username)
    {
        return CartCollection.AllCarts.stream().filter(i -> i.UserName.equals(username)).findFirst().isPresent();
    }



}

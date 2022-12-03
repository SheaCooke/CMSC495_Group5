package Group5Project.WebApp.controller;
import Group5Project.WebApp.Data.*;
import Group5Project.WebApp.model.Item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static Group5Project.WebApp.Data.UserManager.*;
import static Group5Project.WebApp.Data.Menu.MenuItems;
import static Group5Project.WebApp.Data.Menu.PopulateMenuItemsFromDatabase;

@Controller
public class IndexController {

    private List<String> errorMessages = new ArrayList<String>();

    private HelperMethods helperMethods = new HelperMethods();


    @GetMapping("/")
    public String home (Map<String, Object> model) throws SQLException {

        PopulateMenuItemsFromDatabase();

        model.put("errors", errorMessages);

        model.put("MenuItems", MenuItems);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        UserManager.currentUserName = username;

        if(!helperMethods.CartExists(username))
        {
            Cart cart = new Cart(username);

            CartCollection.AllCarts.add(cart);
        }

        if (!UserInformationExists(username))
        {
            CurrentUserInformation currentUserInformation = new CurrentUserInformation(username, 0);

            currentUserInformationList.add(currentUserInformation);
        }

        model.put("NewlyCompletedOrders", GetNotificationsByUserName(UserManager.currentUserName));

        model.put("un", username);

        model.put("role", "USER");

        if (helperMethods.hasRole("ROLE_ADMIN"))
        {
            model.put("role", "ADMIN");

            return "admin";
        }

        return "index";
    }

    @PostMapping("/AddToCart/{ID}")
    public String AddToCart(@PathVariable final int ID) {

        Cart cartToModoify = helperMethods.GetCartByUserName(UserManager.currentUserName);

        //check for max cart quantity
        if (helperMethods.GetCartQuantity(cartToModoify) >= 20)
        {
            if (errorMessages.size() == 0)
            {
                errorMessages.add("Your cart is at its max capacity of 20 items");
            }
            return "redirect:/";
        }
        else
        {
            errorMessages.clear();
        }

        Item itemToClone = MenuItems.stream().filter(i -> i.ID == ID).findFirst().get();

        Item itemToAdd = new Item(itemToClone.ID, itemToClone.ItemName, 1, itemToClone.Price, itemToClone.Description, itemToClone.Category);

        cartToModoify.addItem(itemToAdd);

        return "redirect:/";
    }

    @PostMapping("/RemoveFromCart/{ID}")
    public String DecramentItem(@PathVariable final int ID) {

        boolean removeItem = false;

        Cart cartToModoify = helperMethods.GetCartByUserName(UserManager.currentUserName);

        for (var i : cartToModoify.ItemsInCart)
        {
            if (i.ID == ID)
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
            helperMethods.RemoveItem(ID);
        }

        errorMessages.clear();

        return "redirect:/Cart";
    }

    @PostMapping("/IncramentItem/{ID}")
    public String IncramentItem(@PathVariable final int ID) {

        Cart cartToModoify = helperMethods.GetCartByUserName(UserManager.currentUserName);

        //check for max cart quantity
        if (helperMethods.GetCartQuantity(cartToModoify) >= 20)
        {
            if (errorMessages.size() == 0)
            {
                errorMessages.add("Your cart is at its max capacity of 20 items");
            }

            return "redirect:/Cart";
        }
        else
        {
            errorMessages.clear();
        }

        for (var i : cartToModoify.ItemsInCart)
        {
            if (i.ID == ID)
            {
                i.Quantity++;
            }
        }

        return "redirect:/Cart";
    }

}

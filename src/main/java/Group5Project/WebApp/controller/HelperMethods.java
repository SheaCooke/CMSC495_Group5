package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.Cart;
import Group5Project.WebApp.Data.CartCollection;
import Group5Project.WebApp.Data.UserManager;
import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static Group5Project.WebApp.Data.CompletedOrders.CompletedOrdersList;
import static Group5Project.WebApp.Data.UserManager.Users;

public class HelperMethods {

    public double tryParseDouble(String value, double defaultVal) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getDayMonthYear()
    {

        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        String day = simpleDateFormat.format(date);

        simpleDateFormat = new SimpleDateFormat("MMMM");
        String month = simpleDateFormat.format(date).toUpperCase();

        simpleDateFormat = new SimpleDateFormat("YYYY");
        String year = simpleDateFormat.format(date).toUpperCase();

        String res = day+ "-" + month + "-" + year;

        return res;
    }

    public static boolean CartExists(String username)
    {
        return CartCollection.AllCarts.stream().filter(i -> i.UserName.equals(username)).findFirst().isPresent();
    }

    public static int GetCartQuantity(Cart cart)
    {
        int quantity = 0;

        for (Item item : cart.ItemsInCart)
        {
            quantity += item.Quantity;
        }
        return quantity;
    }

    public static Cart GetCartByUserName(String username)
    {
        if (CartExists(username))
        {
            return CartCollection.AllCarts.stream().filter(i -> i.UserName.equals(username)).findFirst().get();
        }
        else
        {
            return new Cart(UserManager.currentUserName);
        }

    }

    public static boolean hasRole (String roleName)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(roleName));

        return hasUserRole;
    }

    public static void RemoveItem(int ID)
    {
        Cart cartToModoify = GetCartByUserName(UserManager.currentUserName);

        cartToModoify.ItemsInCart.removeIf(j -> j.ID == ID);
    }

    public String getUsernameFromUserID(int userId)
    {
        String idString = String.valueOf(userId);

        return Users.stream().filter(i -> i.getStudentID().equals(idString)).findFirst().get().getUsername();
    }

    public List<Order> Get5RecentOrdersForUser(String username)
    {
        List<Order> ordersByUserName =
                CompletedOrdersList.stream().filter(p -> p.userName.equals(UserManager.currentUserName))
                        .collect(Collectors.toList());

        Collections.sort(ordersByUserName, new Comparator<Order>(){
            public int compare(Order o1, Order o2) {
                return o1.CompletedDate.compareTo(o2.CompletedDate);
            }
        });

        int listSize = ordersByUserName.size();

        return ordersByUserName.subList(Math.max(listSize - 5, 0), listSize);
    }


}

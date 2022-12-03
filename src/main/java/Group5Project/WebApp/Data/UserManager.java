package Group5Project.WebApp.Data;

import Group5Project.WebApp.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserManager {

    public static List<UserModel> Users = new ArrayList<UserModel>();

    public static String currentUserName = "";

    public static List<CurrentUserInformation> currentUserInformationList = new ArrayList<CurrentUserInformation>();

    public static boolean UserInformationExists(String username)
    {
        return currentUserInformationList.stream().filter(i -> i.userName.equals(username)).findFirst().isPresent();
    }

    public static int GetNotificationsByUserName(String userName)
    {
        try
        {
            return currentUserInformationList.stream().filter(i -> i.userName.equals(userName))
                    .findFirst().get().numberOfNotifications;
        }
        catch (NoSuchElementException exception)
        {
            return 0;
        }
    }

    public static void IncrementNotificationCount(String username)
    {
        currentUserInformationList.stream().filter(i -> i.userName.equals(username))
                .findFirst().get().numberOfNotifications += 1;
    }

    public static void ResetNotificationCount(String username)
    {
        try
        {
            currentUserInformationList.stream().filter(i -> i.userName.equals(username))
                    .findFirst().get().numberOfNotifications = 0;
        }
        catch (NoSuchElementException exception)
        {}
    }

}

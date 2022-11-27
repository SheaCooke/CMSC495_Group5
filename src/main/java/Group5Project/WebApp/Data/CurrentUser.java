package Group5Project.WebApp.Data;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser {

    public static String currentUserName = "";

    public static List<CurrentUserInformation> currentUserInformationList = new ArrayList<CurrentUserInformation>();

    public static boolean UserInformationExists(String username)
    {
        return currentUserInformationList.stream().filter(i -> i.userName.equals(username)).findFirst().isPresent();
    }

    public static int GetNotificationsByUserName(String userName)
    {
        return currentUserInformationList.stream().filter(i -> i.userName.equals(userName))
                .findFirst().get().numberOfNotifications;
    }

    public static void IncrementNotificationCount(String username)
    {
        currentUserInformationList.stream().filter(i -> i.userName.equals(username))
                .findFirst().get().numberOfNotifications += 1;
    }

    public static void ResetNotificationCount(String username)
    {
        currentUserInformationList.stream().filter(i -> i.userName.equals(username))
                .findFirst().get().numberOfNotifications = 0;
    }

    //get list of usernames from db
}

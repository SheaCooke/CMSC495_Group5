package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.UserModelCollection;
import Group5Project.WebApp.Data.UserDto;
import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.UserModel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Group5Project.WebApp.WebAppApplication.connection;

@NoArgsConstructor
@Controller
public class RegisterController {

    @Autowired
   private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private boolean validRegistrationInformation = true;

    private List<String> errorMessages = new ArrayList<String>();

    private List<String> successMessages = new ArrayList<String>();


    private UserDto _dto = new UserDto();

//
//
//    public RegisterController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
//        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
//    }

    @GetMapping("/Login")
    public String login (Model model) throws SQLException {

        //load users from database into memory
        LoadUsersFromDatabase();

        model.addAttribute("dto", _dto);
        model.addAttribute("errors", errorMessages);
        model.addAttribute("success", successMessages);

        return "Login";
    }

    @RequestMapping(value="/Logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {

        successMessages.clear();
        errorMessages.clear();
        _dto = new UserDto();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:Login";
    }

    @PostMapping("/register")
    public String Register(Model model, UserDto dto) throws SQLException {

        _dto = dto;

        trimDTOFields(_dto);

        errorMessages.clear();

        successMessages.clear();

          //  grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

            //UserDetails user = new User("test", "test", grantedAuthorities);

//            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);

              //  inMemoryUserDetailsManager.createUser();

        //validate account information
        validateInformation(dto.getPassword(), dto.getVerifyPassword(), dto.getUsername(), dto.getEmail(), dto.getStudentID());

        if (validRegistrationInformation)
        {
            //create new user object
//            UserModel newUser = new UserModel(dto.getEmail(), dto.getUsername(), dto.getPassword(), dto.getStudentID());
//
//            UserModelCollection.Users.add(newUser);
//
//            User.UserBuilder user = User.withDefaultPasswordEncoder();
//
//            inMemoryUserDetailsManager.createUser(user.username(dto.getUsername()).password(dto.getPassword()).roles("USER").build());

            _dto = new UserDto();

            successMessages.add("Successfully registered account");

            String sql = String.format("insert into Customer_Accounts " +
                            "values ('%1$s', '%2$s', '%3$s', '%4$s')",
                    dto.getStudentID(), dto.getEmail(), dto.getUsername(), dto.getPassword());

            Statement statement = connection.createStatement();

            statement.execute(sql);

        }

        validRegistrationInformation = true;


        return "redirect:Login";

    }

    private void validateInformation(String password, String verifyPassword, String username, String email, String studentID)
    {
        if (!PasswordsMatch(password, verifyPassword))
        {
            validRegistrationInformation = false;
            errorMessages.add("Passwords do not match");
        }

        if (username.length() < 3)
        {
            validRegistrationInformation = false;
            errorMessages.add("Username must be at least 3 characters long");
        }
        else if (!UsernameAvailable(username))
        {
            validRegistrationInformation = false;
            errorMessages.add("Username is unavailable");
        }


        if (!UMGCEmail(email))
        {
            validRegistrationInformation = false;
            errorMessages.add("Must use a UMGC email");
        }

        if (!PasswordRequirements(password))
        {
            validRegistrationInformation = false;
            errorMessages.add("Password must contain at least 1 uppercase, 1 lowercase," +
                    "1 special character and be at least 8 characters long");
        }

        if (!ValidStudentID(studentID))
        {
            validRegistrationInformation = false;
            errorMessages.add("Invalid student ID");
        }

    }

    private boolean PasswordsMatch(String password, String verifyPassword)
    {
        return password.equals(verifyPassword);
    }

    private boolean UsernameAvailable(String username)
    {
        return !UserModelCollection.Users.stream().filter(i -> i.getUsername().toLowerCase().equals(username.toLowerCase()))
                .findFirst().isPresent();
    }

    private boolean UMGCEmail(String email)
    {
        if (email.length() <= 10)
        {
            return false;
        }

        return email.substring(email.length() - 8).equals("umgc.edu");
    }

    private boolean PasswordRequirements(String password)
    {
        int requiredLength = 8;

        ArrayList<String> specialCharacters = new ArrayList<String>(Arrays.asList("!","@","#","$","%","^","&","*"));

        ArrayList<String> lowercaseAlphabet = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n",
                                        "o","p","q","r","s","t","u","v","w","x","y","z"));

        ArrayList<String> uppercaseAlphabet = new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
                                        "R","S","T","U","V","W","X","Y","Z"));

        ArrayList<String> passwordArr = new ArrayList<String>(Arrays.asList(password.split("")));

        if(password.length() >= requiredLength &&
                !Collections.disjoint(passwordArr, specialCharacters) &&
                !Collections.disjoint(passwordArr, lowercaseAlphabet) &&
                !Collections.disjoint(passwordArr, uppercaseAlphabet))
        {
            return true;
        }

        return false;

    }

    private boolean ValidStudentID(String studentID)
    {
        return studentID.length() == 7 && tryParseInt(studentID) != -1;
    }

    public int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void trimDTOFields(UserDto dto)
    {
        dto.setEmail(dto.getEmail().trim());
        dto.setPassword(dto.getPassword().trim());
        dto.setVerifyPassword(dto.getVerifyPassword().trim());
        dto.setStudentID(dto.getStudentID().trim());
        dto.setUsername(dto.getUsername().trim());
    }

    private void LoadUsersFromDatabase() throws SQLException {

        String sql = "select * from Customer_Accounts";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();



        while (rs.next())
        {
            String Username = rs.getString("Username");

            if (inMemoryUserDetailsManager.userExists(Username))
            {
                continue;
            }

            String ID = rs.getString("C_AID");
            String Email = rs.getString("Email");

            String Password = rs.getString("Password");

            UserModel newUser = new UserModel(Email, Username, Password, ID);

            UserModelCollection.Users.add(newUser);

            User.UserBuilder user = User.withDefaultPasswordEncoder();

            inMemoryUserDetailsManager.createUser(user.username(Username).password(Password).roles("USER").build());

        }
    }

}

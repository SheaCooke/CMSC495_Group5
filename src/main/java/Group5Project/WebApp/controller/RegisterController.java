package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.UserModelCollection;
import Group5Project.WebApp.Data.UserDto;
import Group5Project.WebApp.model.UserModel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        //validate account information


        //create new user object
        UserModel newUser = new UserModel(dto.getEmail(), dto.getUsername(), dto.getPassword(), dto.getStudentID());

        UserModelCollection.Users.add(newUser);


        //add to collection

        User.UserBuilder user = User.withDefaultPasswordEncoder();


        inMemoryUserDetailsManager.createUser(user.username(dto.getUsername()).password(dto.getPassword()).roles("USER").build());

        return "redirect:Login";
    }

    private boolean InformationIsValid()
    {

    }

    private boolean PasswordsMatch()
    {

    }

    private boolean UsernameAvailable()
    {

    }

    private boolean UMGCEmail()
    {

    }

    private boolean PasswordRequirements()
    {

    }

    private boolean ValidStudentID()
    {
        
    }

}

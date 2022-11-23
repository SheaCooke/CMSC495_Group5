package Group5Project.WebApp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class UserModel {

    @Getter
    public UUID ID = java.util.UUID.randomUUID();

    @Getter
    @Setter
    public String email = "";

    @Getter
    @Setter
    public String username = "";

    @Getter
    @Setter
    public String password = "";

    @Getter
    public String studentID = "";

    //add cart?

    public UserModel(String email, String username, String password, String studentID)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.studentID = studentID;
    }
}

package Group5Project.WebApp.Data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.Serializable;

@Data
public class UserDto {
    @Getter
    @Setter
    private String email = "";
    @Getter
    @Setter
    private String password = "";


}

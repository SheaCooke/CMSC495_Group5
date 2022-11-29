package Group5Project.WebApp.Data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.Serializable;

@Data
public class ItemDto {
    @Getter
    @Setter
    private String ItemName = "";
    @Getter
    @Setter
    private String ItemPrice = "";
    @Getter
    @Setter
    private String ItemCategory = "";
    @Getter
    @Setter
    private String ItemDescription = "";

}
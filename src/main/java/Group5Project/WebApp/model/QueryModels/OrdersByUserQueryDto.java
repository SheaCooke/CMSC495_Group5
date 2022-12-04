package Group5Project.WebApp.model.QueryModels;

import Group5Project.WebApp.model.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrdersByUserQueryDto {
    @Getter
    @Setter
    private String UserName = "";

    @Getter
    @Setter
    private String UserID = "";
}

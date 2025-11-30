package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class Userlogin {
    @NotNull
    private String username;

    @NotNull
    private String password;

//    @NotNull
//    private String email;

}

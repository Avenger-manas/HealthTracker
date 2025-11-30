package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private List<String> roles;

}

package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Hospital")
public class Hospital {
    @Id
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String hospitalName;

    @NotNull
    private String registration_number;

    @NotNull
    private String hospital_type;

    @NotNull
    private String factor_available;

    @NotNull
    private String ownership_type;

    @NotNull
    private String address;

    @NotNull
    private String city;


    private List<String> roles;

}

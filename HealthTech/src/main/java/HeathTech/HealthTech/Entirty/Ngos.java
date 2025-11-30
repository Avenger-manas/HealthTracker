package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Ngo")
public class Ngos {
    @Id
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String ngoName;

    @NotNull
    private String registration_number;

    @NotNull
    private String contact_number;

    @NotNull
    private String address;

    @NotNull
    private String city;



    private List<String> roles;
}

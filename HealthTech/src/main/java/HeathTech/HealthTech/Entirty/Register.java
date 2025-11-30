package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Document(collection = "register")
@Data
public class Register {
    @Id
    private String id;

    private String username;

    @NotNull
    private String ngoId;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    private String contact_number;

    @NotNull
    private String factor_type;

    @NotNull
    private String Adar_Card;

    @NotNull
    private String city;

    @NotNull
    private LocalDateTime createdAt=LocalDateTime.now();





}

package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.NotNull;

@Data
public class NgoEntity {
    @NotNull
    private String ngoName;

    @NotNull
    private String city;


}

package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class HospitalSearch {

    @NotNull
    private String hospitalName;

    @NotNull
    private String city;

}

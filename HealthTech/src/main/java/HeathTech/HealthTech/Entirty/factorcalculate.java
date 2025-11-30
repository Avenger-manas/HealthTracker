package HeathTech.HealthTech.Entirty;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class factorcalculate {

    @NotNull
    private String heamophilia_type;

    @NotNull
    private String situation;

    @NotNull
    private String factorType;


//    @NotNull
//    private String factor_level;

    @NotNull
    private String weight;

//    @NotNull
//    private String age;

}

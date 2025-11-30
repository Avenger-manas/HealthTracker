package HeathTech.HealthTech.Controller;

import HeathTech.HealthTech.Entirty.factorcalculate;
import HeathTech.HealthTech.Service.FactorCalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai_factor")
@CrossOrigin(origins = "http://localhost:5173")
public class AiFactorDetails {

    @Autowired
    private FactorCalculateService factorCalculateService;

    @PostMapping("/aiFactorCalculate")
    public ResponseEntity<?> factor_availablity(@RequestBody factorcalculate factorcalculate){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(factorCalculateService.calculateFactor(factorcalculate),HttpStatus.CREATED);
    }
}


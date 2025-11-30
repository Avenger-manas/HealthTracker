package HeathTech.HealthTech.Controller;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Repository.hospitaldatabase;
import HeathTech.HealthTech.Service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/factor_available")
public class Factor_Available {
    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/factor_yes")
    public ResponseEntity<?> factor_availablity(@RequestParam String factor){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username=authentication.getName();

        return new ResponseEntity<>(hospitalService.setFactor(username,factor), HttpStatus.CREATED);

    }
}

package HeathTech.HealthTech.Controller;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.HospitalSearch;
import HeathTech.HealthTech.Entirty.NgoEntity;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Service.FindHospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/serachHospital")
public class SercahHospital {
    @Autowired
    private FindHospital findHospital;
    @PostMapping("/search")
    public Page<Hospital> findngo(@RequestBody HospitalSearch hospitalSearch){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return  findHospital.searchHospital(hospitalSearch);
    }

    @PostMapping("/saerch/hospiatlcity")
    public ResponseEntity<?> findngo(@RequestParam String City){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            return new ResponseEntity<>(findHospital.findhospiatlcitybase(City),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("City Not Exits",HttpStatus.BAD_REQUEST);
        }

    }
}

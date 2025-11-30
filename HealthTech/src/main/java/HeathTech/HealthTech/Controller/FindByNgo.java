package HeathTech.HealthTech.Controller;

import HeathTech.HealthTech.Entirty.NgoEntity;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Repository.ngodatabase;
import HeathTech.HealthTech.Service.FindNgo;
import HeathTech.HealthTech.Service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/searchNgo")
@CrossOrigin(origins = "http://localhost:5173")
public class FindByNgo {

    @Autowired
    private FindNgo findNgo;

    @PostMapping("/find")
    public Page<Ngos> findngo(@RequestBody NgoEntity ngo_name){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  findNgo.searchngo(ngo_name);
    }

    @PostMapping("/saerch/ngocity")
    public ResponseEntity<?> findngo(@RequestParam String City){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            return new ResponseEntity<>(findNgo.findngocitybase(City),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("City Not Exits",HttpStatus.BAD_REQUEST);
        }

    }
}

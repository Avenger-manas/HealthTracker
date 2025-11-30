package HeathTech.HealthTech.Service;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Repository.hospitaldatabase;
import HeathTech.HealthTech.Repository.ngodatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
public class NgoService {
    @Autowired
    private ngodatabase ngodatabase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;
//create ngo
    public ResponseEntity<?> createngo(Ngos ngos){
        String encodepassword=passwordEncoder.encode(ngos.getPassword());
        ngos.setPassword(encodepassword);
        ngos.setRoles(Arrays.asList("NGO"));
        ngodatabase.save(ngos);
        emailService.sendMail(ngos.getEmail(),"Hello "+ngos.getUsername(),"Congrats Succesfully Sing-Up In HeatlthTech");
        return new ResponseEntity<>("NGO Crate Succesfully", HttpStatus.OK);
    }

    //find ngo
    public Ngos findngo(String username) {
        Ngos ngo = ngodatabase.findByUsername(username);
        if (ngo == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with username: " + username
            );
        }
        return ngo;

    }

    public boolean existsByUsername(String username) {
        return ngodatabase.findByUsername(username) !=null;
    }

}

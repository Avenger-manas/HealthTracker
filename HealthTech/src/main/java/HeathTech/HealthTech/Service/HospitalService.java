package HeathTech.HealthTech.Service;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.User;
import HeathTech.HealthTech.Repository.hospitaldatabase;
import HeathTech.HealthTech.Repository.userdatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
public class HospitalService {
    @Autowired
    private hospitaldatabase hospitaldatabase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> createhospital(Hospital hospital){
        String encodepassword=passwordEncoder.encode(hospital.getPassword());
        hospital.setPassword(encodepassword);
        hospital.setRoles(Arrays.asList("HOSPITAL"));
        hospitaldatabase.save(hospital);
        emailService.sendMail(hospital.getEmail(),"Hello "+hospital.getUsername(),"Congrats Succesfully Sing-Up In HeatlthTech");
        return new ResponseEntity<>("User Crate Succesfully", HttpStatus.OK);
    }

    //find user
    public Hospital findhospital(String username) {
        Hospital hospital = hospitaldatabase.findByUsername(username);
        if (hospital == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with username: " + username
            );
        }
        return hospital;

    }

    //set entity
    public ResponseEntity<?> setFactor(String hospitalname,String factor){
        Hospital hospital = hospitaldatabase.findByUsername(hospitalname);
        try {
            hospital.setFactor_available(factor);
            hospitaldatabase.save(hospital);
        } catch (Exception e) {
            return new ResponseEntity<>("Not Available Hospital Not Exits",HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("Succesfully Set Factor",HttpStatus.OK);
    }

    public boolean existsByUsername(String username) {
        return hospitaldatabase.findByUsername(username) !=null;
    }


}

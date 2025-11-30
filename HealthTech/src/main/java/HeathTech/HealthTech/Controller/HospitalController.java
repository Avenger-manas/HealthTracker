package HeathTech.HealthTech.Controller;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.Hospitalentity;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Entirty.User;
import HeathTech.HealthTech.JwtToken.JwtToken;
import HeathTech.HealthTech.Repository.hospitaldatabase;
import HeathTech.HealthTech.Service.EmailService;
import HeathTech.HealthTech.Service.HospitalService;
import HeathTech.HealthTech.Service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private JwtToken jwttoken;

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private hospitaldatabase hospitaldatabase;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private EmailService emailService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    AuthenticationManager authenticationManager;

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @PostMapping("/hospital_registration")
    public ResponseEntity<?>newregistration(@RequestBody Hospital hospital){
        boolean exits=hospitalService.existsByUsername(hospital.getUsername());

        boolean check=pattern.matcher(hospital.getEmail()).matches();

        if(!check)  return ResponseEntity.status(HttpStatus.CONFLICT).body("User Email Incorrect Please Enter Valid Email");


        if(exits){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Is Already Exits");
        }

        if(hospitaldatabase.existsByEmail(hospital.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This Email Is Already User Please Use New Email Address");
        }

        return new ResponseEntity<>(hospitalService.createhospital(hospital), HttpStatus.CREATED);
    }


    @PostMapping("/hospiatllogin")
    public ResponseEntity<?> loginuser(@RequestBody Hospitalentity hospitalentity){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(hospitalentity.getUsername(), hospitalentity.getPassword()));

            UserDetails userDetails = myUserDetails.loadUserByUsername(hospitalentity.getUsername());

            //check roles based

            Hospital userdb=hospitalService.findhospital(hospitalentity.getUsername());

            if(userdb==null){
                return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
            }

            String roles= String.valueOf(userdb.getRoles());

            String role = roles.replace("[", "").replace("]", "");

            if(!role.equalsIgnoreCase("HOSPITAL")){
                return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
            }

            String jwt = jwttoken.generateToken(userDetails);

            return new ResponseEntity<>(jwt, HttpStatus.OK);


        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Incoorect Username Password", HttpStatus.BAD_REQUEST);
        }


}

    @PostMapping("/forget-passwordhospital")
    public ResponseEntity<?> forgetpassword(@RequestParam String email){
        emailService.sendMail(email,"Change Password",frontendUrl+"/hospital/forget/password");
        return new ResponseEntity("Check Email " + email , HttpStatus.CREATED);
    }


    @PostMapping("/password-hospital")
    public ResponseEntity<?> forgatpassword(@RequestParam String email, @RequestParam String password) {
        try {
            Hospital olduser = hospitaldatabase.findByEmail(email);
            if (olduser == null) {
                return new ResponseEntity<>("Please Enter Valid Email ", HttpStatus.NO_CONTENT);
            }

            olduser.setPassword(password);

            hospitalService.createhospital(olduser);

            return new ResponseEntity("Hi " + email + " Succesfully Upadate Password", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}

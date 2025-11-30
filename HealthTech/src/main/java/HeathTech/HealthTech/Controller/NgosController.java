package HeathTech.HealthTech.Controller;

import HeathTech.HealthTech.Entirty.NgoLogin;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Entirty.User;
import HeathTech.HealthTech.JwtToken.JwtToken;
import HeathTech.HealthTech.Repository.ngodatabase;
import HeathTech.HealthTech.Repository.userdatabase;
import HeathTech.HealthTech.Service.EmailService;
import HeathTech.HealthTech.Service.MyUserDetails;
import HeathTech.HealthTech.Service.NgoService;
import HeathTech.HealthTech.Service.UserService;
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
@RequestMapping("/Ngo")
public class NgosController {

    @Autowired
    private JwtToken jwttoken;

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";


    @Autowired
    private ngodatabase ngodatabase;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private NgoService ngoService;

    @Autowired
    private EmailService emailService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    AuthenticationManager authenticationManager;

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    //this controller create user
    @PostMapping("/createngo")
    public ResponseEntity<?> cretaeuser(@RequestBody Ngos ngo){
        boolean exits=ngoService.existsByUsername(ngo.getUsername());

        boolean check=pattern.matcher(ngo.getEmail()).matches();

        if(!check)  return ResponseEntity.status(HttpStatus.CONFLICT).body("User Email Incorrect Please Enter Valid Email");


        if(exits){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Is Already Exits");
        }

        if(ngodatabase.existsByEmail(ngo.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This Email Is Already User Please Use New Email Address");
        }

        return new ResponseEntity<>(ngoService.createngo(ngo), HttpStatus.CREATED);

    }

    @PostMapping("/ngologin")
    public ResponseEntity<?> loginuser(@RequestBody NgoLogin ngo){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ngo.getUsername(), ngo.getPassword()));

            UserDetails userDetails = myUserDetails.loadUserByUsername(ngo.getUsername());

            //check roles based

            Ngos userdb=ngoService.findngo(ngo.getUsername());

            if(userdb==null){
                return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
            }

            String roles= String.valueOf(userdb.getRoles());

            String role = roles.replace("[", "").replace("]", "");

            if(!role.equalsIgnoreCase("NGO")){
                return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
            }

            String jwt = jwttoken.generateToken(userDetails);

            return new ResponseEntity<>(jwt, HttpStatus.OK);


        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Incoorect Username Password", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/forget-passwordngo")
    public ResponseEntity<?> forgetpassword(@RequestParam String email){
        emailService.sendMail(email,"Change Password",frontendUrl+"/ngo/forget/password");
        return new ResponseEntity("Check Email " + email , HttpStatus.CREATED);
    }


    @PostMapping("/password-ngo")
    public ResponseEntity<?> forgatpassword(@RequestParam String email, @RequestParam String password) {
        try {
            Ngos olduser = ngodatabase.findByEmail(email);
            if (olduser == null) {
                return new ResponseEntity<>("Please Enter Valid Email ", HttpStatus.NO_CONTENT);
            }

            olduser.setPassword(password);

            ngoService.createngo(olduser);

            return new ResponseEntity("Hi " + email + " Succesfully Upadate Password", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

package HeathTech.HealthTech.Controller;

import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Entirty.Register;
import HeathTech.HealthTech.Service.FindNgo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration_user")
@CrossOrigin(origins = "http://localhost:5173")
public class RegisterController {

    @Autowired
    private FindNgo findNgo;



    // USER REGISTRATION REGISTER NGO USE USER
    @PostMapping("/user_register")
    public ResponseEntity<?> register(@RequestBody Register register) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        register.setUsername(authentication.getName());

        return new ResponseEntity<>(findNgo.registerfindngoclass(register), HttpStatus.CREATED);
    }

    // GET USERS REGISTERED FOR THIS NGO
    @GetMapping("/getregisteruser")
    public ResponseEntity<?> getAllUserRegister() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String ngousername= authentication.getName();

            return new ResponseEntity<>(findNgo.givealluserregisterdata(ngousername),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
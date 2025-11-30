package HeathTech.HealthTech.Service;

import HeathTech.HealthTech.Entirty.User;
import HeathTech.HealthTech.Repository.userdatabase;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    private userdatabase userdatabase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?>createuser(User user){
        String encodepassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodepassword);
        user.setRoles(Arrays.asList("USER"));
        userdatabase.save(user);
        emailService.sendMail(user.getEmail(),"Hello "+user.getUsername(),"Congrats Succesfully Sing-Up In HeatlthTech");
        return new ResponseEntity<>("User Crate Succesfully", HttpStatus.OK);
    }

    //find user
    public User finduser(String username) {

        User user = userdatabase.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with username: " + username
            );
        }
        return user;

    }

    public boolean existsByUsername(String username) {
        return userdatabase.findByUsername(username) !=null;
    }

}
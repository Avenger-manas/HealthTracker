package HeathTech.HealthTech.Service;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Entirty.User;
import HeathTech.HealthTech.Repository.hospitaldatabase;
import HeathTech.HealthTech.Repository.ngodatabase;
import HeathTech.HealthTech.Repository.userdatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private userdatabase userdatabase; // User Entity ka repository

    @Autowired
    private hospitaldatabase hospitalRepository; // Hospital Entity ka repository

    @Autowired
    private ngodatabase ngodatabase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1️Pehle User table me check karo
        User user = userdatabase.findByUsername(username);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword()) // already encoded
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }

        // 2 Agar User nahi mila to Hospital table me check karo
        Hospital hospital = hospitalRepository.findByUsername(username);
        if (hospital != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(hospital.getUsername())
                    .password(hospital.getPassword()) // already encoded
                    .roles(hospital.getRoles().toArray(new String[0]))
                    .build();
        }

        Ngos ngos = ngodatabase.findByUsername(username);
        if (ngos != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(ngos.getUsername())
                    .password(ngos.getPassword()) // already encoded
                    .roles(ngos.getRoles().toArray(new String[0]))
                    .build();
        }

        // 3 Dono me nahi mila to exception
        throw new UsernameNotFoundException("No user or hospital found with username: " + username);
    }
}

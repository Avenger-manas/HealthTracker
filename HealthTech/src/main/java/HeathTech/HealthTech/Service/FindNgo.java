package HeathTech.HealthTech.Service;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.NgoEntity;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Entirty.Register;
import HeathTech.HealthTech.Repository.ngodatabase;
import HeathTech.HealthTech.Repository.registerDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FindNgo {

    @Autowired
    private ngodatabase ngodatabase;

    @Autowired
    private registerDB registerdb;

    @Autowired
    private EmailService emailService;


    public Page<Ngos> searchngo(NgoEntity ngo){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("ngoName").and(Sort.by("city")));

        String name = (ngo.getNgoName() == null || ngo.getNgoName().isEmpty()) ? "" : ngo.getNgoName();
        String city = (ngo.getCity() == null || ngo.getCity().isEmpty()) ? "" : ngo.getCity();

        return ngodatabase.findByNgoNameContainingIgnoreCaseAndCityContainingIgnoreCase(
                name, city, pageable
        );
    }


    //find hospiatl city base
    public ResponseEntity<?> findngocitybase(String City){
        try {
            List<Ngos> ngo=ngodatabase.findByCity(City);
            return new ResponseEntity<>(ngo, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


   // regiester user ngo
    @Transactional
    public ResponseEntity<?> registerfindngoclass(Register registration) {
        try {
           Register registations= registerdb.findByUsername(registration.getUsername());
            if(registations!=null){
                return new ResponseEntity<>("This User Is Alraedy Register", HttpStatus.NO_CONTENT);
            }

            registerdb.save(registration);

            try {
                emailService.sendMail(registration.getEmail(),"Hello "+registration.getName(),"Congrats Succesfully Register Your Register Number "+registration.getId());
            } catch (Exception e) {
                System.err.println("Error sending email to " + registration.getEmail() + ": " + e.getMessage());

            }



            return new ResponseEntity<>("Registration Successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Registration UnSuccessfully");
        }
    }

    public ResponseEntity<?> givealluserregisterdata(String ngoname){
        try {
            Ngos ngo=ngodatabase.findByUsername(ngoname);
            String ngoid=ngo.getId();
           List<Register> register=registerdb.findByNgoId(ngoid);
            return new ResponseEntity<>(register,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}

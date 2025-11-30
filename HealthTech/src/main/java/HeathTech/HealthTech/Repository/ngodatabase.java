package HeathTech.HealthTech.Repository;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Entirty.User;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ngodatabase extends MongoRepository<Ngos,String> {
    Ngos findByUsername(String username);

    boolean existsByEmail(String email);

    Ngos findByEmail(String email);

    List<Ngos> findByCity(String City);


//    Page<Ngos> findByNgoName(String ngoName, String city, Pageable pageable);
Page<Ngos> findByNgoNameContainingIgnoreCaseAndCityContainingIgnoreCase(
        String ngoName, String city, Pageable pageable
);

}

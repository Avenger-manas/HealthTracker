package HeathTech.HealthTech.Repository;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Entirty.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface hospitaldatabase extends MongoRepository<Hospital,String> {
    Hospital findByUsername(String username);

    boolean existsByEmail(String email);

    Hospital findByEmail(String email);

    Page<Hospital> findByHospitalName(String hospitalName, String city, Pageable pageable);

    List<Hospital> findByCity(String City);
}

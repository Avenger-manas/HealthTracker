package HeathTech.HealthTech.Repository;

import HeathTech.HealthTech.Entirty.Register;
import HeathTech.HealthTech.Entirty.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface registerDB  extends MongoRepository<Register,String> {

    Register findByUsername(String username);

    List<Register> findByNgoId(String ngoId);
}

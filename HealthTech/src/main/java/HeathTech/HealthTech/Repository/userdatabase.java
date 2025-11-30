package HeathTech.HealthTech.Repository;

import HeathTech.HealthTech.Entirty.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userdatabase extends MongoRepository<User,String> {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}


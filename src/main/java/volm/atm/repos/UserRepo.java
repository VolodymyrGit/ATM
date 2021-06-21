package volm.atm.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import volm.atm.models.User;

import java.util.Optional;


@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    Optional<User> findByCardNumberEquals(String cardNumber);
}
package volm.atm.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import volm.atm.models.User;
import volm.atm.models.UserTransactions;

import java.util.List;


@Repository
public interface UserTransactionsRepo extends CrudRepository<UserTransactions, Long> {

    List<UserTransactions> findAllByUserFromEqualsOrUserToEquals(User userFrom, User userTo);
}

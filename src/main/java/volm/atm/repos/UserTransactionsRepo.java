package volm.atm.repos;

import org.springframework.data.repository.CrudRepository;
import volm.atm.models.UserTransactions;

public interface UserTransactionsRepo extends CrudRepository<UserTransactions, Long> {
}

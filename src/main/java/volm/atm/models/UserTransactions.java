package volm.atm.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserTransactions {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User userFrom;

    @ManyToOne
    private User userTo;

    private LocalDateTime transactionTime;

    private BigDecimal amount;

    public UserTransactions(User userFrom, User userTo, LocalDateTime transactionTime, BigDecimal amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.transactionTime = transactionTime;
        this.amount = amount;
    }
}

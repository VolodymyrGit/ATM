package volm.atm.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @ManyToOne
    private User userFrom;

    @ManyToOne
    private User userTo;

    private LocalDateTime transactionTime;

    private BigDecimal amount;

    public UserTransactions(OperationType operationType,
                            User userFrom,
                            User userTo,
                            LocalDateTime transactionTime,
                            BigDecimal amount) {
        this.operationType = operationType;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.transactionTime = transactionTime;
        this.amount = amount;
    }
}

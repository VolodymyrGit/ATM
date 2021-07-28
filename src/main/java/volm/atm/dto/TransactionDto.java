package volm.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import volm.atm.models.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

    private OperationType operationType;
    private String cardNumberUserFrom;
    private String cardNumberUserTo;
    private LocalDateTime transactionTime;
    private BigDecimal amount;
}

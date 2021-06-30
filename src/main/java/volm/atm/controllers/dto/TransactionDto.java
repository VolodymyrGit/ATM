package volm.atm.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import volm.atm.models.OperationType;
import volm.atm.models.UserTransactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Long transactionId;
    private OperationType operationType;
    private String cardNumberUserFrom;
    private String cardNumberUserTo;
    private LocalDateTime transactionTime;
    private BigDecimal amount;


    public static TransactionDto fromModelToDto(UserTransactions transactions) {
        return TransactionDto.builder()
                .transactionId(transactions.getId())
                .operationType(transactions.getOperationType())
                .cardNumberUserFrom(transactions.getUserFrom() != null ? transactions.getUserFrom().getCardNumber() : null)
                .cardNumberUserTo(transactions.getUserTo() != null ? transactions.getUserTo().getCardNumber() : null)
                .transactionTime(transactions.getTransactionTime())
                .amount(transactions.getAmount())
                .build();
    }
}

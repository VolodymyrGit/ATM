package volm.atm.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class TransactionsRequestDto {

    private String cardNumber;


    @Positive(message = "Amount must be strictly positive")
    private BigDecimal amount;
}

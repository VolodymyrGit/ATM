package volm.atm.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class BalanceResponseDto {

    private String cardNumber;
    private BigDecimal balance;
}

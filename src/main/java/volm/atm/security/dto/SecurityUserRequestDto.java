package volm.atm.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
public class SecurityUserRequestDto {

    @Size(max = 16, min = 16, message = "The card number must consist of 16 digits")
    private String cardNumber;

    @Size(max = 4, min = 4, message = "The pin code must consist of 4 digits")
    private String pinCode;
}

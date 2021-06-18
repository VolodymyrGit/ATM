package volm.atm.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequestDto {

    private String cardNumber;
    private String pinCode;
    private String userName;
}

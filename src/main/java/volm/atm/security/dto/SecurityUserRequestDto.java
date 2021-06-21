package volm.atm.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class SecurityUserRequestDto {

    private String cardNumber;
    private String pinCode;
}

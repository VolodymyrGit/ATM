package volm.atm.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSecurityDto {

    private Long cardNumber;
    private String pinCode;
}

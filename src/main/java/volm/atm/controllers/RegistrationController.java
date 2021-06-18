package volm.atm.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import volm.atm.models.User;
import volm.atm.repos.UserRepo;
import volm.atm.security.dto.RegistrationRequestDto;

import java.math.BigDecimal;


@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @PostMapping
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationRequestDto requestDto) {

        String securePass = passwordEncoder.encode(requestDto.getPinCode());

        User newUser = User.builder()
                .userName(requestDto.getUserName())
                .cardNumber(requestDto.getCardNumber())
                .pinCode(securePass)
                .balance(BigDecimal.valueOf(0))
                .build();

        userRepo.save(newUser);

        return ResponseEntity.ok().build();
    }
}

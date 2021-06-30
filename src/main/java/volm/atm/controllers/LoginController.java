package volm.atm.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import volm.atm.models.User;
import volm.atm.security.JwtProvider;
import volm.atm.security.dto.SecurityUserRequestDto;
import volm.atm.service.UserService;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid SecurityUserRequestDto requestDto) {

        User userFromDB = userService.getUser(requestDto.getCardNumber());

        if (passwordEncoder.matches(requestDto.getPinCode(), userFromDB.getPinCode())) {

            String token = jwtProvider.generateToken(requestDto);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

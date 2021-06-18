package volm.atm.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import volm.atm.models.User;
import volm.atm.repos.UserRepo;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @GetMapping
    public User getUser(@RequestParam(name = "cardNumber") Long cardNumber,
                        @RequestParam(name = "pinCode") String pinCode) {

        Optional<User> userFromDB = userRepo.findByCardNumberEquals(cardNumber);

        String encodedPassword = passwordEncoder.encode(pinCode);
        return userFromDB.get();
    }
}

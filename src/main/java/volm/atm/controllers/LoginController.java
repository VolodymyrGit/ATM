package volm.atm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import volm.atm.models.User;
import volm.atm.repos.UserRepo;
import volm.atm.security.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/login")

    public String getLoginView() {

        userRepo.save(new User("Bob", 1234456778900987L, "sdfsdskdfshf2387r5hb2rb",
                new ArrayList<>(Collections.singletonList(Role.BANK_USER)), 12546L));

        return "login";
    }

//    @PutMapping("/login")
//    public String postLogin(@RequestParam(name = "cardNumber") Long login, String password, Model model) {
//
//        Optional<User> userFromDB = userRepo.findByCardNumberEquals(login);
//
//        String securePin
//        return "login";
//    }
}
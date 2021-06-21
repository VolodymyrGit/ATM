package volm.atm.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import volm.atm.exceptions.EntityNotFoundException;
import volm.atm.models.User;
import volm.atm.repos.UserRepo;
import volm.atm.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {

        User userFromDB = userRepo.findByCardNumberEquals(login)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        if (passwordEncoder.matches(password, userFromDB.getPassword())) {
            return Optional.of(userFromDB);
        }
        return Optional.empty();
    }
}

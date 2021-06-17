package volm.atm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import volm.atm.repos.UserRepo;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {
        return userRepo.findByCardNumberEquals(Long.parseLong(cardNumber))
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user by this " + cardNumber));
    }
}
package volm.atm.security;


import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import volm.atm.exceptions.InvalidTokenException;
import volm.atm.security.dto.SecurityUserRequestDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;
    private  final Gson gson;


    public String generateToken(SecurityUserRequestDto securityUserRequestDto) {

        Date date = Date.from(Instant.from(LocalDateTime.now().plusHours(1)));
        String subject = gson.toJson(securityUserRequestDto);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
        return false;
    }


    public SecurityUserRequestDto getLoginPasswordFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        return gson.fromJson(subject, SecurityUserRequestDto.class);
    }
}

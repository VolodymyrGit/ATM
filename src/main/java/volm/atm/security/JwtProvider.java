package volm.atm.security;


import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import volm.atm.exceptions.InvalidTokenException;
import volm.atm.security.dto.UserSecurityDto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;
    private  final Gson gson;


    public String generateToken(UserSecurityDto userSecurityDto) {

        Date date = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String subject = gson.toJson(userSecurityDto);

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


    public UserSecurityDto getLoginPasswordFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        return gson.fromJson(subject, UserSecurityDto.class);
    }
}

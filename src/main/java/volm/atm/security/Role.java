package volm.atm.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    BANK_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
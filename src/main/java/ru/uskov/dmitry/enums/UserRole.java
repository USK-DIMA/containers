package ru.uskov.dmitry.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Dmitry on 11.03.2017.
 */
public enum UserRole implements GrantedAuthority {

    ROLE_SERVICE_ADMIN, ROLE_CONTRAGENT_ADMIN, ROLE_CONTRAGENT_USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

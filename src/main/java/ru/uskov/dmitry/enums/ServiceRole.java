package ru.uskov.dmitry.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Dmitry on 11.03.2017.
 */
public enum ServiceRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

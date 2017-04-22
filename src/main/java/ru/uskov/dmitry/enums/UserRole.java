package ru.uskov.dmitry.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Dmitry on 11.03.2017.
 */
public enum UserRole implements GrantedAuthority {

    ROLE_ADMIN("Администратор"), ROLE_MANAGER("Менеджер");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}

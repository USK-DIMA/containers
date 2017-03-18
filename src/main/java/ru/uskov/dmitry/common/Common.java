package ru.uskov.dmitry.common;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.uskov.dmitry.entity.User;

/**
 * Created by Dmitry on 18.03.2017.
 */
public class Common {
    public static User getCurrentUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof User) {
            return (User) object;
        }
        return null;
    }
}

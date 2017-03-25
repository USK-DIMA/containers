package ru.uskov.dmitry.common;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.enums.UserRole;

import java.util.Set;

/**
 * Created by Dmitry on 18.03.2017.
 */
public class Common {


    public static boolean isCurrentUserAdmin() {
        return currentUserHasAnyRole(UserRole.ROLE_SERVICE_ADMIN, UserRole.ROLE_CONTRAGENT_ADMIN);
    }

    public static boolean isCurrentUserUser() {
        return currentUserHasAnyRole(UserRole.ROLE_CONTRAGENT_USER);
    }

    public static boolean currentUserHasAnyRole(UserRole... userRoles) {
        User user = getCurrentUser();
        if (user == null) {
            return false;
        }
        Set<UserRole> roles = user.getRoles();
        if (CollectionUtils.isEmoty(roles)) {
            return false;
        }
        for (UserRole role : userRoles) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;

    }


    public static User getCurrentUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof User) {
            return (User) object;
        }
        return null;
    }

    public static boolean isCurrentUserAdminService() {
        return currentUserHasAnyRole(UserRole.ROLE_SERVICE_ADMIN);
    }

    public static boolean isCurrentUserAdminContragent() {
        return currentUserHasAnyRole(UserRole.ROLE_CONTRAGENT_ADMIN);
    }
}

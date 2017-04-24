package ru.uskov.dmitry.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.uskov.dmitry.dao.UserDao;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.enums.UserRole;

import java.util.Set;

/**
 * Created by Dmitry on 18.03.2017.
 */
@Component
public class Common {

    @Autowired
    UserDao userDao;


    public static boolean isCurrentUserAdmin() {
        return currentUserHasAnyRole(UserRole.ROLE_ADMIN);
    }

    public static boolean isCurrentUserManager() {
        return currentUserHasAnyRole(UserRole.ROLE_MANAGER);
    }

    public static boolean currentUserHasAnyRole(UserRole... userRoles) {
        User user = getCurrentUser();
        if (user == null) {
            return false;
        }
        Set<UserRole> roles = user.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
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

    public void updateCurrentUser() {
        User user = userDao.getUser(getCurrentUser().getId());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getRoles()));
    }
}

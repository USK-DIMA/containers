package ru.uskov.dmitry.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Dmitry on 24.04.2017.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NewLoginValidator.class)
public @interface NewLogin {
    String message() default "Логин уже занят";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

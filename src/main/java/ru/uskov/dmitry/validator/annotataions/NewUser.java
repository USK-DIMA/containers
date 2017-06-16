package ru.uskov.dmitry.validator.annotataions;

import ru.uskov.dmitry.validator.constraints.NewUserConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Dmitry on 15.06.2017.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NewUserConstraint.class)
public @interface NewUser {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

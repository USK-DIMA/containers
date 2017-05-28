package ru.uskov.dmitry.annotation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional(propagation = Propagation.MANDATORY)
public @interface TransactionalMandatory {
}

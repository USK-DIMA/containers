package ru.uskov.dmitry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Created by Dmitry on 04.03.2017.
 */
public abstract class AbstractController {

    @Autowired
    protected ResourceBundleMessageSource messageSource;

}

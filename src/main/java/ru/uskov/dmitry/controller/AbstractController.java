package ru.uskov.dmitry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.Model;

import java.util.Locale;

/**
 * Created by Dmitry on 04.03.2017.
 */
public abstract class AbstractController {

    @Autowired
    protected ResourceBundleMessageSource messageSource;

    protected void addMessageSource(Model model, String paramName, String messageKey, Locale locale) {
        model.addAttribute(paramName, messageSource.getMessage(messageKey, null, locale));
    }

    protected void addMessageSource(Model model, String paramName, Locale locale) {
        addMessageSource(model, paramName, paramName, locale);
    }

}

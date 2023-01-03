package com.armaan.dematic.exceptions.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Function to use the Pattern Validator to Implement the ConstraintValidator Interface to program the annotation.
 */
public class EnumBookTypePatternValidator implements ConstraintValidator<EnumBookTypePattern, Enum<?>> {
    private Pattern pattern;

    /**
     * Function to initialize pattern
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(EnumBookTypePattern constraintAnnotation) {
        try {
            pattern = Pattern.compile(constraintAnnotation.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    /**
     * Function to check validity of Enum type
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return boolean value to see if the Enum value is valid
     */
    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Matcher m = pattern.matcher(value.name());
        return m.matches();
    }
}

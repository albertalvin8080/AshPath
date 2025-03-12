package org.featherlessbipeds.ashpath.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GraveLocationValidator implements ConstraintValidator<ValidGraveLocation, String> {

    @Override
    public void initialize(ValidGraveLocation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String location, ConstraintValidatorContext context) {
        if (location == null) {
            return false;
        }
        // Rejeita números decimais (floats)
        return !location.matches(".*\\d+\\.\\d+.*");
    }
}
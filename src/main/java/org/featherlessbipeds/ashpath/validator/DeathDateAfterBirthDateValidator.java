package org.featherlessbipeds.ashpath.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.featherlessbipeds.ashpath.entity.Deceased;

public class DeathDateAfterBirthDateValidator implements ConstraintValidator<DeathDateAfterBirthDate, Deceased> {

    @Override
    public boolean isValid(Deceased deceased, ConstraintValidatorContext context) {
        if (deceased == null || deceased.getBirthDate()== null) {
            return true;
        }
        
        return deceased.getBirthDate().before(deceased.getDeathDate());
    } 
}

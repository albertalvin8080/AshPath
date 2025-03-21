package org.featherlessbipeds.ashpath.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MinDateValidator implements ConstraintValidator<MinDate, Date> {

    private Date minDate; // Data minima permitida

    @Override
    public void initialize(MinDate constraintAnnotation) {
        try {
            String minDateStr = constraintAnnotation.value();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.minDate = dateFormat.parse(minDateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de data invalido. Use o formato yyyy-MM-dd.", e);
        }
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.before(minDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                            .replace("{value}", new SimpleDateFormat("yyyy-MM-dd").format(minDate)))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
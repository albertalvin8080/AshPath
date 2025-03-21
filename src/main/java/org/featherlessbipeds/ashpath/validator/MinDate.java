package org.featherlessbipeds.ashpath.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinDateValidator.class)
public @interface MinDate {
    String message() default "A data deve ser posterior a {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value(); // Valor minimo da data (no formato ISO-8601: yyyy-MM-dd)
}
package org.featherlessbipeds.ashpath.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.regex.Pattern;

public class ContactNumbersValidator implements ConstraintValidator<ContactNumbers, Set<String>>
{
    // BR style, US style, Poland style.
    public static final Pattern PHONE_PATTERN = Pattern.compile("^(?:\\d{2} \\d{4}-\\d{4}|\\d{3}-\\d{3}-\\d{4}|\\+\\d{2} \\d{3} \\d{3} \\d{3})$");
    public static final int MAX_CONTACT_NUMBERS = 3;
    
    @Override
    public boolean isValid(Set<String> contactNumbers, ConstraintValidatorContext context)
    {
        if (contactNumbers == null || contactNumbers.isEmpty())
            return false;

        if (contactNumbers.size() > MAX_CONTACT_NUMBERS)
            return false;
        
        for (String number : contactNumbers)
        {
            if (number == null || !PHONE_PATTERN.matcher(number).matches())
            {
                return false;
            }
        }

        return true; // All numbers passed validation
    }
}

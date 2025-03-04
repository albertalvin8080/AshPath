package org.featherlessbipeds.ashpath.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class SpecializationValidator implements ConstraintValidator<Specialization, String>
{
   private List<String> validSpecializations;
   
   public SpecializationValidator(){
   validSpecializations = List.of(
        "Autopsy Technician",
        "Forensic Investigation",
        "Forensic Analysis",
        "Pathologist",
        "Forensic Examiner",
        "Toxicologist",
        "DNA Analyst",
        "Crime Scene Analyst",
        "Autopsy",
        "Mata Leão",
        "Fakie to fakie 900"
    ); 
   }
   
    @Override
    public boolean isValid(String specialization, ConstraintValidatorContext cvc) {
        return validSpecializations.contains(specialization);
    }
}

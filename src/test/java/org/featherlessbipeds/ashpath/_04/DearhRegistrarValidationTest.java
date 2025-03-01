package org.featherlessbipeds.ashpath._04;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import org.featherlessbipeds.ashpath.entity.DeathRegistrar;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.MatcherAssert;

public class DearhRegistrarValidationTest extends TestHelper
{
    @Test(expected = ConstraintViolationException.class)
    public void persistirDeathRegistrarInvalido()
    {
        DeathRegistrar deathRegistrar = null;
        try
        {
            deathRegistrar = new DeathRegistrar();
            deathRegistrar.setUsername("Invalid username"); // Invalid username (contains white space)
            deathRegistrar.setPassword("111111111111"); // Invalid password 
            deathRegistrar.setFullName("Invalid Name 123"); // Invalid full name (contains numbers)
            deathRegistrar.setEmail("invalid-email"); // Invalid email format
            deathRegistrar.addContactNumber("123"); // Invalid contact number (too short)
            deathRegistrar.addContactNumber("1234567890123456"); // Invalid contact number (too long)
            deathRegistrar.addContactNumber("123-456-7890"); // Valid contact number
            deathRegistrar.setRegistrationDate(Date.from(LocalDate
                    .now()
                    .plusDays(2)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
            )); // Invalid registration date (future)
            deathRegistrar.setLastActivityDate(new Date()); // Valid last activity date

            em.persist(deathRegistrar);
            em.flush();
        }
        catch (ConstraintViolationException ex)
        {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation ->
            {
                MatcherAssert.assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.email: Deve ser um endereço de e-mail bem formado"),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.fullName: Deve possuir de 2 a 40 caracteres, incluindo letras, espaços e apóstrofes."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.contactNumbers: Devem possuir de 10 a 15 digitos e no máximo 3 números de contato."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.password: Deve possuir de 8 a 20 caracteres, pelo menus um caractere especial, pelo menos uma letra maiúscula, pelo menos um digito."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.username: Deve possuir de 4 a 16 caracteres, incluindo letras, dígitos, apóstrofes, hífens, underscores e pontos."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.lastActivityDate: Deve ser uma data passada ou presente."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.registrationDate: Deve ser uma data passada ou presente.")
                        )
                );
            });

            assertEquals(6, constraintViolations.size());
            assertNull(deathRegistrar.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarDeathRegistrarInvalido()
    {
        DeathRegistrar deathRegistrar = em.createQuery("SELECT d FROM DeathRegistrar d WHERE d.email like :email", DeathRegistrar.class)
                .setParameter("email", "jakubfarobec@gmail.com")
                .getSingleResult();

        deathRegistrar.setEmail("invalid-email");
        deathRegistrar.setPassword("111111111111"); // Invalid password 
        deathRegistrar.setFullName("Invalid Name 123"); // Invalid full name (contains numbers)
        deathRegistrar.setUsername("Manus, Father of the Abyss"); // Invalid full name (contains whitespace and comma)
        deathRegistrar.setLastActivityDate(Date.from(LocalDate
                    .now()
                    .plusDays(2)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
            )); // Invalid registration date (future)
        // More than MAX_CONTACT_NUMBERS==3
        deathRegistrar.addContactNumber("123-456-7890"); // Valid contact number
        deathRegistrar.addContactNumber("123-456-7891"); // Valid contact number
        deathRegistrar.addContactNumber("123-456-7892"); // Valid contact number
        deathRegistrar.addContactNumber("123-456-7893"); // Valid contact number

        try
        {
            em.flush();
        }
        catch (ConstraintViolationException ex)
        {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation ->
            {
                MatcherAssert.assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.contactNumbers: Devem possuir de 10 a 15 digitos e no máximo 3 números de contato."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.email: Deve ser um endereço de e-mail bem formado"),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.fullName: Deve possuir de 2 a 40 caracteres, incluindo letras, espaços e apóstrofes."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.username: Deve possuir de 4 a 16 caracteres, incluindo letras, dígitos, apóstrofes, hífens, underscores e pontos."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.password: Deve possuir de 8 a 20 caracteres, pelo menus um caractere especial, pelo menos uma letra maiúscula, pelo menos um digito."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.lastActivityDate: Deve ser uma data passada ou presente."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.DeathRegistrar.registrationDate: Deve ser uma data passada ou presente.")
                        )
                );
            });

            assertEquals(6, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}

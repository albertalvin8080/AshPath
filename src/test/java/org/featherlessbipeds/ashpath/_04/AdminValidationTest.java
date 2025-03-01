package org.featherlessbipeds.ashpath._04;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import org.featherlessbipeds.ashpath.entity.Admin;
import org.featherlessbipeds.ashpath.entity.AdminRole;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.hamcrest.MatcherAssert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class AdminValidationTest extends TestHelper
{
    @Test(expected = ConstraintViolationException.class)
    public void persistirAdminInvalido()
    {
        Admin adm = null;
        try
        {
            adm = new Admin();
            adm.setUsername("Invalid username"); // Invalid username (contains white space)
            adm.setPassword("222222222"); // Invalid password 
            adm.setRole(AdminRole.SYSTEM_ADMIN);

            em.persist(adm);
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
                                startsWith("class org.featherlessbipeds.ashpath.entity.Admin.password: Deve possuir de 8 a 20 caracteres, pelo menus um caractere especial, pelo menos uma letra maiúscula, pelo menos um digito."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Admin.username: Deve possuir de 4 a 16 caracteres, incluindo letras, dígitos, apóstrofes, hífens, underscores e pontos.")
                        )
                );
            });

            assertEquals(2, constraintViolations.size());
            assertNull(adm.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarAdminInvalido()
    {
        Admin adm = em.createQuery("SELECT a FROM Admin a WHERE a.username like :username", Admin.class)
                .setParameter("username", "Johan")
                .getSingleResult();

        adm.setPassword("222222222"); // Invalid password 
        adm.setUsername("Adaptive_Moment_Estimation_With_Decoupled_Weight_Decay"); // Invalid full name (more than 40 characters)

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
                                startsWith("class org.featherlessbipeds.ashpath.entity.Admin.username: Deve possuir de 4 a 16 caracteres, incluindo letras, dígitos, apóstrofes, hífens, underscores e pontos."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Admin.password: Deve possuir de 8 a 20 caracteres, pelo menus um caractere especial, pelo menos uma letra maiúscula, pelo menos um digito.")
                        )
                );
            });

            assertEquals(2, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}

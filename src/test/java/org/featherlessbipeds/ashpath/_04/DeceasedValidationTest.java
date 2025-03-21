package org.featherlessbipeds.ashpath._04;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.Set;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.hamcrest.MatcherAssert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DeceasedValidationTest extends TestHelper {

    @Test(expected = ConstraintViolationException.class)
    public void persistirDeceasedInvalido() {

        Deceased deceased = null;
        try {
            deceased = new Deceased();
            deceased.setName("invalid-123");
            deceased.setCauseOfDeath("");
            deceased.setBirthDate(Timestamp.valueOf("2028-04-15 00:00:00"));
            deceased.setDeathDate(Timestamp.valueOf("2027-07-01 13:45:00"));

            em.persist(deceased);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation
                    -> {
                MatcherAssert.assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.: A data de morte deve ser posterior à data de nascimento."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.name: O nome deve possuir de 2 a 40 caracteres, incluindo letras, espaços e apóstrofes."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.causeOfDeath: não deve estar em branco."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.deathDate: deve ser uma data passada ou presente."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.birthDate: deve ser uma data passada.")
                        )
                );
            });

            assertEquals(5, constraintViolations.size());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarDeceasedInvalido() {
        
        Deceased deceased = em.createQuery("SELECT d FROM Deceased d WHERE d.name like :name", Deceased.class)
                .setParameter("name", "Pontiff Sulyvahn")
                .getSingleResult();

        deceased.setName("invalid-123");
        deceased.setCauseOfDeath("");
        deceased.setBirthDate(Timestamp.valueOf("2028-04-15 00:00:00"));
        deceased.setDeathDate(Timestamp.valueOf("2027-07-01 13:45:00"));

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation
                    -> {
                MatcherAssert.assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.: A data de morte deve ser posterior à data de nascimento."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.name: O nome deve possuir de 2 a 40 caracteres, incluindo letras, espaços e apóstrofes."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.causeOfDeath: não deve estar em branco."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.deathDate: deve ser uma data passada ou presente."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Deceased.birthDate: deve ser uma data passada.")
                        )
                );
            });

            assertEquals(5, constraintViolations.size());
            throw ex;
        }
    }
}

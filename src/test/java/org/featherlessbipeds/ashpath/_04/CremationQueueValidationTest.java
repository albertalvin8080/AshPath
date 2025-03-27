package org.featherlessbipeds.ashpath._04;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.MatcherAssert;

public class CremationQueueValidationTest extends TestHelper {

    private Date criarData(String dataStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.parse(dataStr);
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistirCremationQueueInvalido() throws ParseException {
        CremationQueue cremationQueue = null;
        try {
            cremationQueue = new CremationQueue();

            // Configuracao invalida
            cremationQueue.setEnteredDate(criarData("2035-11-07 14:30")); // Data futura (invalida)
            cremationQueue.setEnteredDate(criarData("1300-01-01 00:00")); // Data anterior a 1988-01-01 (invalida)

            // Colecoes vazias (invalidas)
            cremationQueue.getDeceasedSet().clear(); // DeceasedSet vazio
            cremationQueue.getNecrotomistSet().clear(); // NecrotomistSet vazio

            em.persist(cremationQueue);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            // Verifica as mensagens de erro
            constraintViolations.forEach(violation -> MatcherAssert.assertThat(
                    violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                    CoreMatchers.anyOf(
                            startsWith("class org.featherlessbipeds.ashpath.entity.CremationQueue.enteredDate: A data de entrada deve ser posterior a 1500-01-01 00:00"),
                            startsWith("class org.featherlessbipeds.ashpath.entity.CremationQueue.deceasedSet: O DeceasedSet não pode ser vazio."),
                            startsWith("class org.featherlessbipeds.ashpath.entity.CremationQueue.necrotomistSet: O Necrotomist não pode ser vazio.")
                    )
            ));

            // Verifica o numero de violacoes
            assertEquals(3, constraintViolations.size());
            assertNull(cremationQueue.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarCremationQueueInvalido() throws ParseException {
        // Busca uma CremationQueue valida no banco de dados
        CremationQueue cremationQueue = em.createQuery("SELECT cq FROM CremationQueue cq WHERE cq.enteredDate = :enteredDate", CremationQueue.class)
                .setParameter("enteredDate", criarData("2025-02-01 14:00:00"))
                .getSingleResult();

        // Configuracao invalida
        cremationQueue.setEnteredDate(criarData("1200-01-01 00:00")); // Data anterior a 1988-01-01 (invalida)
        cremationQueue.getDeceasedSet().clear(); // DeceasedSet vazio
        cremationQueue.getNecrotomistSet().clear(); // NecrotomistSet vazio

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            // Verifica as mensagens de erro
            constraintViolations.forEach(violation -> {
                MatcherAssert.assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class org.featherlessbipeds.ashpath.entity.CremationQueue.enteredDate: A data de entrada deve ser posterior a 1500-01-01 00:00"),
                                startsWith("class org.featherlessbipeds.ashpath.entity.CremationQueue.deceasedSet: O DeceasedSet não pode ser vazio."),
                                startsWith("class org.featherlessbipeds.ashpath.entity.CremationQueue.necrotomistSet: O Necrotomist não pode ser vazio.")
                        )
                );
            });

            // Verifica o numero de violacoes
            assertEquals(3, constraintViolations.size());
            throw ex;
        }
    }

//    @Test
//    public void persistirCremationQueueValido() throws ParseException {
//        CremationQueue cremationQueue = new CremationQueue();
//
//        // Configuracao valida
//        cremationQueue.setEnteredDate(criarData("2023-01-01 10:00")); // Data valida
//
//        Deceased dc = new Deceased();
//        dc.setName("Satoru Mikomi");
//        dc.setCauseOfDeath("Stabbed while protecting a friend");
//        dc.setBirthDate(Timestamp.valueOf("1981-04-15 00:00:00"));
//        dc.setDeathDate(Timestamp.valueOf("2018-07-01 13:45:00"));
//
//
//
//        cremationQueue.addDeceased(dc); // Adiciona um falecido valido
//        Necrotomist necrotomist = new Necrotomist();
//        necrotomist.setSpecialization("Autopsy");
//        necrotomist.setName("Paulo Chair");
//
//        Deceased deceased = em.find(Deceased.class, 1L);
//        necrotomist.addDeceased(deceased);
//
//        cremationQueue.addNecrotomist(necrotomist); // Adiciona um necrotomista valido
//
//        em.persist(cremationQueue);
//        em.flush();
//
//        assertNotNull(cremationQueue.getId());
//    }
}

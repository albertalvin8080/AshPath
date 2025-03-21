package org.featherlessbipeds.ashpath._01;

import java.sql.Timestamp;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.junit.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;

public class CremationQueueTest extends TestHelper
{
    @Test
    public void persistCremationQueue_AddsToDb_WhenSuccessful() {
        CremationQueue cq = new CremationQueue();

        // Preenche a data de entrada
        cq.setEnteredDate(new Date());

        // Cria e preenche uma instância de Deceased
        Deceased deceased = new Deceased();

        deceased.setName("Satoru Mikomi");
        deceased.setCauseOfDeath("Stabbed while protecting a friend");
        deceased.setBirthDate(Timestamp.valueOf("1981-04-15 00:00:00"));
        deceased.setDeathDate(Timestamp.valueOf("2018-07-01 13:45:00"));

        deceased.setName("John Doe"); // Exemplo de campo obrigatório
        cq.addDeceased(deceased);

        // Cria e preenche uma instância de Necrotomist
        Necrotomist necrotomist = new Necrotomist();
        necrotomist.setName("Smith"); // Campo obrigatório (2 a 40 caracteres, apenas letras, espaços e apóstrofes)
        necrotomist.setSpecialization("Autopsy Technician"); // Campo obrigatório (deve estar na lista de especializações válidas)
        cq.addNecrotomist(necrotomist);

        try {
            // Persiste a CremationQueue
            em.persist(cq);
            em.flush();

            // Verifica se o ID foi gerado
            assertNotNull(cq.getId());
        } catch (ConstraintViolationException ex) {
            // Captura e exibe as violações de validação
            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                System.err.println(
                        "Violação: " + violation.getPropertyPath() + " - " + violation.getMessage()
                );
            }
            throw ex; // Re-lança a exceção para falhar o teste
        }
    }

    @Test
    public void findCremationQueue_ReturnsEnity_WhenSuccessful()
    {
        CremationQueue persistedCq = em.find(CremationQueue.class, 1L);
        assertNotNull(persistedCq);
        assertEquals(persistedCq.getEnteredDate(), Timestamp.valueOf("2023-12-09 01:00:00"));
    }

    @Test
    public void deleteCremationQueue_returnsNull_WhenSucessful()
    {
        // 1L would conflict with the order of tests.
//        CremationQueue cremationQueue = em.find(CremationQueue.class, 1L);
        CremationQueue cremationQueue = em.find(CremationQueue.class, 2L);

        assertNotNull(cremationQueue);

        em.remove(cremationQueue);

        em.flush();

        assertNull(em.find(CremationQueue.class, 2L));
    }
}

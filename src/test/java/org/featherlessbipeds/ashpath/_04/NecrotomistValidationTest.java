package org.featherlessbipeds.ashpath._04;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.Set;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Grave;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.hamcrest.MatcherAssert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NecrotomistValidationTest extends TestHelper {

    @Test(expected = ConstraintViolationException.class)
    public void create_invalid_necrotomist() {

        String invalidSpecialization = "Dentista";//Especializacao invalida
        try {
            Necrotomist newNecro = new Necrotomist();
            newNecro.setName("joao 123");// Nome com numeros
            newNecro.setSpecialization(invalidSpecialization);//Especializacao invalida

            em.persist(newNecro);
            em.flush();

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation
                    -> {
                MatcherAssert.assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class org.featherlessbipeds.ashpath.entity.Necrotomist.specialization: Especializacao invalida"),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Necrotomist.name: O nome deve possuir de 2 a 40 caracteres, nao incluso numeros")
                        )
                );
            });

            assertEquals(2, constraintViolations.size());
            throw ex;
        }

    }

    @Test(expected = ConstraintViolationException.class)
    public void edit_invalid_necrotomist() {

        String name = "Serjao Foguetes";

        String invalidSpecialization = "Beach Tenis";// Especializacao que nao existe
        String invalidName = "Serjinho 4466";//Nome com numeros

        Necrotomist serjaoFoguetes = em.createQuery("SELECT n FROM Necrotomist n WHERE n.name like :name", Necrotomist.class)
                .setParameter("name", name)
                .getSingleResult();

        serjaoFoguetes.setName(invalidName);//Nome com numeros
        serjaoFoguetes.setSpecialization(invalidSpecialization);// Especializacao que nao existe
            
          
        
        Grave g1 = new Grave();
        g1.setLocation("Maceio");
        em.persist(g1);
        
      
        Grave g2 = new Grave();
        g2.setLocation("Maceio");
        em.persist(g2);
        
        //Queue que ja existe
        CremationQueue queue = em.find(CremationQueue.class, 11L);

        //O Serjao Foguetes ja tem 3 defuntos associados a ele, vou add + 2 pra poder dar pau
        Deceased d1 = new Deceased();
        d1.setName("Cirilo");
        d1.setGrave(g1);
        d1.setCremationQueue(queue);
        d1.setBirthDate(Timestamp.valueOf("1960-05-15 00:00:00"));
        d1.setDeathDate(Timestamp.valueOf("2023-10-01 12:00:00"));
        d1.setCauseOfDeath("Heart Attack");
        d1.setNecrotomist(serjaoFoguetes);
        em.persist(d1);
        em.flush(); 
        
        Deceased d2 = new Deceased();
        d2.setName("Valeria");
        d2.setGrave(g2);
        d2.setCremationQueue(queue);
        d2.setBirthDate(Timestamp.valueOf("1970-05-16 00:00:00"));
        d2.setDeathDate(Timestamp.valueOf("2022-10-01 12:00:00"));
        d2.setCauseOfDeath("Heart Attack");
        d2.setNecrotomist(serjaoFoguetes);
        em.persist(d2);
        
        try {            
          em.flush();
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation
                    -> {
                MatcherAssert.assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class org.featherlessbipeds.ashpath.entity.Necrotomist.specialization: Especializacao invalida"),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Necrotomist.name: O nome deve possuir de 2 a 40 caracteres, nao incluso numeros"),
                                startsWith("class org.featherlessbipeds.ashpath.entity.Necrotomist.deceasedSet: Numero maximo violado")
                        )
                );
            });

            assertEquals(3, constraintViolations.size());
            throw ex;

        }

    }
}

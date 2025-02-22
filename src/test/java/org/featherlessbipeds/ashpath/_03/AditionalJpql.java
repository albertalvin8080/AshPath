package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.regex.Pattern;
import org.featherlessbipeds.ashpath.dto.DeceasedDto;
import org.featherlessbipeds.ashpath.entity.DeathRegistrar;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import static org.junit.Assert.*;
import org.junit.Test;

public class AditionalJpql extends GenericTest
{
    @Test
    public void newDeceasedDto_from_DeceasedEntity()
    {
        String causeOfDeath = "Heart Attack";

        TypedQuery<DeceasedDto> query = em.createQuery("""
            SELECT NEW org.featherlessbipeds.ashpath.dto.DeceasedDto(d.id, d.name, d.causeOfDeath, d.birthDate, d.deathDate)
            FROM Deceased d
            WHERE d.causeOfDeath = ?1
        """, DeceasedDto.class);
        query.setParameter(1, causeOfDeath);

        List<DeceasedDto> list = query.getResultList();
        list.forEach(d -> assertEquals(
                causeOfDeath, d.getCauseOfDeath()
            )
        );
    }

    @Test
    public void deceased_LeftJoinGrave()
    {
        TypedQuery<Deceased> query = em.createQuery("""
            SELECT d FROM Deceased d LEFT JOIN d.grave g
        """, Deceased.class);
        List<Deceased> list = query.getResultList();

        assertEquals(13, list.size());
    }
    
    // https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/criteria/JoinType.html
//    @Test
//    public void grave_RightJoinDeceased()
//    {
//        TypedQuery<Grave> query = em.createQuery("""
//            SELECT g FROM Grave g RIGHT JOIN g.deceased d
//        """, Grave.class);
//        List<Grave> list = query.getResultList();
//
//        assertEquals(13, list.size());
//    }

    @Test
    public void necrotomist_HavingMoreThanOne_CremationQueue()
    {
        TypedQuery<Object[]> query = em.createQuery("""
            SELECT n.name, COUNT(ncq)
            FROM Necrotomist n
            JOIN n.cremationQueueSet ncq
            GROUP BY n.name
            HAVING COUNT(ncq) > 1
        """, Object[].class);

        List<Object[]> list = query.getResultList();
        list.forEach(o ->
        {
            assertTrue((long) o[1] > 1);
            MatcherAssert.assertThat((String) o[0], CoreMatchers.anyOf(
                    CoreMatchers.equalTo("Mohammed"),
                    CoreMatchers.equalTo("Serjao Foguetes"),
                    CoreMatchers.equalTo("Sirris of the Sunless Realms")
            ));
        });
    }
    
    @Test
    public void deathRegistrar_IsEmptyContactNumbers() {
        TypedQuery<DeathRegistrar> query = em.createQuery("""
            SELECT dr
            FROM DeathRegistrar dr
            WHERE dr.contactNumbers IS EMPTY
        """, DeathRegistrar.class);
        List<DeathRegistrar> list = query.getResultList();
        
        list.forEach(dr -> assertTrue(dr.getContactNumbers().isEmpty()));
    }
    
    @Test
    public void deathRegistrar_ConcatNameAndEmail_Regex()
    {
        TypedQuery<String> query = em.createQuery("""
            SELECT CONCAT(dr.fullName, ' - ', dr.email) FROM DeathRegistrar dr
        """, String.class);
        List<String> list = query.getResultList();

        assertFalse(list.isEmpty());
        
        String regex = "^[A-Za-z'\\s]+\\s{1}-\\s{1}[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        
        System.out.println(list);
        list.forEach(fullNameAndEmail -> assertTrue(
           pattern.matcher(fullNameAndEmail).matches()
        ));
    }
}

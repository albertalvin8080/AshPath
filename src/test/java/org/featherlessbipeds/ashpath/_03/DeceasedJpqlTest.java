package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.function.Function;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DeceasedJpqlTest extends GenericTest {

    @Test
    public void deceased_FindByName() {
        TypedQuery<Deceased> query = em.createQuery("SELECT d FROM Deceased d WHERE d.name LIKE :name", Deceased.class);
        query.setParameter("name", "Siegmeyer of Catarina");
        List<Deceased> deceasedList = query.getResultList();

        deceasedList.forEach(deceased -> {
            assertEquals("Siegmeyer of Catarina", deceased.getName());
        });

        assertEquals(1, deceasedList.size());
    }

    @Test
    public void deceased_FindByName_NamedQuery() {
        TypedQuery<Deceased> query = em.createNamedQuery("Deceased.FindByName", Deceased.class);
        query.setParameter("name", "Dusk of Oolacile");
        List<Deceased> deceasedList = query.getResultList();

        deceasedList.forEach(deceased -> {
            assertEquals("Dusk of Oolacile", deceased.getName());
        });

        assertEquals(1, deceasedList.size());
    }

    @Test
    public void deceased_FindBuried() {
        TypedQuery<Deceased> query = em.createQuery("SELECT d FROM Deceased d JOIN d.grave g", Deceased.class);
        List<Deceased> deceasedList = query.getResultList();
        assertEquals(12, deceasedList.size());

        deceasedList.forEach(deceased -> {
            assertNotNull(deceased.getGrave());
        });
    }

    @Test
    public void deceased_CountByNecrotomist() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(d) FROM Deceased d JOIN d.necrotomist n WHERE n.id = :necrotomistId", Long.class);
        query.setParameter("necrotomistId", 9L);
        Long count = query.getSingleResult();

        assertEquals(1L, count.longValue());
    }

    @Test
    public void deceased_CountGroupByCauseOfDeathOrderByCauseOfDeath() {
        Query query = em.createQuery("SELECT d.causeOfDeath, COUNT(d) FROM Deceased d GROUP BY d.causeOfDeath ORDER BY d.causeOfDeath");
        List<Object[]> list = query.getResultList();

        Function<Object[], String> extract = (o) -> {
            return String.format("%s:%d", (String) o[0], (long) o[1]);
        };

        assertEquals("Accident:1", extract.apply(list.get(0)));
        assertEquals("Car Accident:1", extract.apply(list.get(1)));
        assertEquals("Death by balls.:1", extract.apply(list.get(2)));
    }
}

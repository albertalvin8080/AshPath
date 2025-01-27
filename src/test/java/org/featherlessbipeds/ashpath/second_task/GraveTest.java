package org.featherlessbipeds.ashpath.second_task;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import org.featherlessbipeds.ashpath.entity.Grave;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import static org.featherlessbipeds.ashpath.utils.IdsUtil.*;

public class GraveTest extends TestHelper
{

    @Test
    public void updateGrave()
    {
        var newLocation = "newLocation";

        Long id = GV_ID_4;
        Grave grave = em.find(Grave.class, id);
        grave.setLocation(newLocation);

        em.flush();

        String jpql = "SELECT g FROM Grave g WHERE g.id = ?1";
        TypedQuery<Grave> query = em.createQuery(jpql, Grave.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        grave = query.getSingleResult();

        assertEquals(grave.getLocation(), newLocation);
    }

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void updateGraveMerge()
    {
        var newLocation = "updatedLocation";

        Long id = GV_ID_4;
        Grave grave = em.find(Grave.class, id);
        grave.setLocation(newLocation);

        em.clear();
        grave = em.merge(grave);

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        grave = em.find(Grave.class, id, properties);

        assertEquals(grave.getLocation(), newLocation);
    }

    @Test
    public void removeGrave()
    {
        Long id = GV_ID_6;
        Grave grave = em.find(Grave.class, id);
        em.remove(grave);
        grave = em.find(Grave.class, id);
        assertNull(grave);
    }
}

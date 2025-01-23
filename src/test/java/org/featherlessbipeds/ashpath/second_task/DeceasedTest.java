package org.featherlessbipeds.ashpath.second_task;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import static org.featherlessbipeds.ashpath.utils.IdsUtil.*;

public class DeceasedTest extends TestHelper
{
    @Test
    public void updateDeceased()
    {
        var newName = "newName";
        var newCauseOfDeath = "newCause";
        
        Long id = DC_ID_5;
        Deceased dc = em.find(Deceased.class, id);
        dc.setName(newName);
        dc.setCauseOfDeath(newCauseOfDeath);
        
        em.flush();
        
        String jpql = "SELECT d FROM Deceased d WHERE d.id = ?1";
        TypedQuery<Deceased> query = em.createQuery(jpql, Deceased.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        dc = query.getSingleResult();
        
        assertEquals(dc.getName(), newName);
        assertEquals(dc.getCauseOfDeath(), newCauseOfDeath);
    }
    
    @Test
    @SuppressWarnings("UnusedAssignment")
    public void updateDeceasedMerge()
    {
        var newName = "updatedName";
        var newCauseOfDeath = "updatedCause";
        
        Long id = DC_ID_5;
        Deceased dc = em.find(Deceased.class, id);
        dc.setName(newName);
        dc.setCauseOfDeath(newCauseOfDeath);
        
        em.clear();
        dc = (Deceased) em.merge(dc);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        dc = em.find(Deceased.class, id, properties);
        
        assertEquals(dc.getName(), newName);
        assertEquals(dc.getCauseOfDeath(), newCauseOfDeath);
    }
    
    @Test
    public void removeDeceased()
    {
        Long id = DC_ID_6;
        Deceased dc = em.find(Deceased.class, id);
        em.remove(dc);
        dc = em.find(Deceased.class, id);
        assertNull(dc);
    }
}

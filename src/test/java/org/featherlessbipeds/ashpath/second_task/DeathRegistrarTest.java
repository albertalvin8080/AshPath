package org.featherlessbipeds.ashpath.second_task;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.DeathRegistrar;
import static org.junit.Assert.*;
import org.junit.Test;

public class DeathRegistrarTest extends TestHelper
{
    @Test
    public void updateDeathRegistrar()
    {
        var newEmail = "newemail@mail.com";
        var newFullName = "Never Say Ever";
        
        Long id = 5L;
        DeathRegistrar dr = em.find(DeathRegistrar.class, id);
        dr.setEmail(newEmail);
        dr.setFullName(newFullName);
        
        em.flush();
        
        String jpql = "SELECT a FROM DeathRegistrar a WHERE a.id = ?1";
        TypedQuery<DeathRegistrar> query = em.createQuery(jpql, DeathRegistrar.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        dr = query.getSingleResult();
        
        assertEquals(dr.getEmail(), newEmail);
        assertEquals(dr.getFullName(), newFullName);
    }

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void updateDeathRegistrarMerge()
    {
        var newEmail = "somemail@mail.com";
        var newFullName = "Help Me";
        
        Long id = 5L;
        DeathRegistrar dr = em.find(DeathRegistrar.class, id);
        dr.setEmail(newEmail);
        dr.setFullName(newFullName);
        
        em.clear();
        dr = (DeathRegistrar) em.merge(dr);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        dr = em.find(DeathRegistrar.class, id, properties);
        
        assertEquals(dr.getEmail(), newEmail);
        assertEquals(dr.getFullName(), newFullName);
    }

    @Test
    public void removeDeathRegistrar()
    {
        Long id = 6L;
        DeathRegistrar dr = em.find(DeathRegistrar.class, id);
        em.remove(dr);
        dr = em.find(DeathRegistrar.class, id);
        assertNull(dr);
    }
}

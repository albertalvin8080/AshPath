package org.featherlessbipeds.ashpath.second_task;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.MatcherAssert;
import static org.junit.Assert.*;
import org.junit.Test;

public class NecrotomistTest extends TestHelper
{
   @Test
    public void updateNecrotomist()
    {
        var newName = "Khabib Nurmagomedov";
        var newSpecialization = "Mata Leão";
        
        Long id = 3L;
        Necrotomist necro = em.find(Necrotomist.class, id);
        necro.setName(newName);
        necro.setSpecialization(newSpecialization);
        em.flush();
        
        String jpql = "SELECT a FROM Necrotomist a WHERE a.id = ?1";
        TypedQuery<Necrotomist> query = em.createQuery(jpql, Necrotomist.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        necro = query.getSingleResult();
        
        assertEquals(necro.getName(), newName);
        assertEquals(necro.getSpecialization(), newSpecialization);
    }

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void updateNecrotomistMerge()
    {
        var newName = "Tony Hawk";
        var newSpecialization = "Fakie to fakie 900";
        
        Long id = 4L;
        Necrotomist necro = em.find(Necrotomist.class, id);
        necro.setName(newName);
        necro.setSpecialization(newSpecialization);
        
        em.clear();
        necro = (Necrotomist) em.merge(necro);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        necro = em.find(Necrotomist.class,id, properties);
        
        assertEquals(necro.getName(), newName);
        assertEquals(necro.getSpecialization(), newSpecialization);
    }

    @Test
    public void removeNecrotomist()
    {
        Long id = 4L;
        Necrotomist necro = em.find(Necrotomist.class, id);
        em.remove(necro);
        necro = em.find(Necrotomist.class,id);
        assertNull(necro);
                
    }
}

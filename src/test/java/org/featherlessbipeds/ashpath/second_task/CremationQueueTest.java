package org.featherlessbipeds.ashpath.second_task;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.junit.Test;

import static org.featherlessbipeds.ashpath.utils.IdsUtil.*;
import static org.junit.Assert.*;

public class CremationQueueTest extends TestHelper
{

    @Test
    public void testUpdate() throws ParseException
    {
        //final Long id = 3L;
        CremationQueue cremationQueue = em.find(CremationQueue.class, CQ_ID_3);
        assertNotNull("CremationQueue not found", cremationQueue);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = dateFormat.parse("2025-01-07");
        cremationQueue.setEnteredDate(newDate);
        em.flush();

        String jpql = "SELECT c FROM CremationQueue c WHERE c.id = :id";
        TypedQuery<CremationQueue> query = em.createQuery(jpql, CremationQueue.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter("id", CQ_ID_3);
        CremationQueue updatedCremationQueue = query.getSingleResult();

        assertNotNull("EnteredDate should not be null for ID: " + CQ_ID_3, updatedCremationQueue.getEnteredDate());
        assertEquals("EnteredDate mismatch for ID: " + CQ_ID_3, newDate, updatedCremationQueue.getEnteredDate());
    }

    @Test
    public void testUpdateWithMerge() throws ParseException
    {
        CremationQueue cremationQueue = em.find(CremationQueue.class, CQ_ID_4);
        assertNotNull("CremationQueue not found for ID: " + CQ_ID_4, cremationQueue);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = dateFormat.parse("2025-01-07");
        cremationQueue.setEnteredDate(newDate);
        em.clear();

        em.merge(cremationQueue);
        em.flush();

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        CremationQueue updatedCremationQueue = em.find(CremationQueue.class, CQ_ID_4, properties);

        assertNotNull("EnteredDate should not be null for ID: " + CQ_ID_4, updatedCremationQueue.getEnteredDate());
        assertEquals("EnteredDate mismatch for ID: " + CQ_ID_4, newDate, updatedCremationQueue.getEnteredDate());
    }

    @Test
    public void testRemove()
    {
        CremationQueue cremationQueue = em.find(CremationQueue.class, CQ_ID_5);
        assertNotNull("CremationQueue not found for ID: " + CQ_ID_5, cremationQueue);

        em.remove(cremationQueue);
        em.flush();

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        CremationQueue deletedCremationQueue = em.find(CremationQueue.class, CQ_ID_5, properties);

        assertNull("CremationQueue should be null after removal", deletedCremationQueue);
    }
}

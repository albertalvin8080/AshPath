package org.featherlessbipeds.ashpath;

import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CremationQueueTest extends TestHelper
{
    @Test
    public void persistCremationQueue_AddsToDb_WhenSuccessful()
    {
        CremationQueue cq = new CremationQueue();

        cq.setEnteredDate(new Date());

        em.persist(cq);
        em.flush();

        CremationQueue persistedCq = em.find(cq.getClass(), cq.getId());
        assertNotNull(persistedCq);
        assertEquals(persistedCq.getId(), cq.getId());
    }

    @Test
    public void findCremationQueue_ReturnsEnity_WhenSuccessful()
    {
        CremationQueue persistedCq = em.find(CremationQueue.class, 1L);
        assertNotNull(persistedCq);
        assertNotNull(persistedCq.getEnteredDate());
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

package org.featherless.bipeds.ashpath;

import org.featherless.bipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

public class CremationQueueTest extends TestHelper {

    @Test
    public void persistCremationQueue_AddsToDb_WhenSuccessful() {
        CremationQueue cq = new CremationQueue();


        cq.setEnteredDate(new Date());

        em.persist(cq);
        em.flush();

        CremationQueue persistedCq = em.find(cq.getClass(), cq.getId());
        assertNotNull(persistedCq);
        assertEquals(persistedCq.getId(), cq.getId());

    }

    @Test
    public void findCremationQueue_ReturnsEnity_WhenSuccessful() throws ParseException {

        CremationQueue persistedCq = em.find(CremationQueue.class, 1L);
        Long id = persistedCq.getId();
        assertNotNull(persistedCq);
        assertNotNull(persistedCq.getEnteredDate());

    }

    @Test
    public void deleteCremationQueue_returnsNull_WhenSucessful(){

        CremationQueue cremationQueue = em.find(CremationQueue.class, 1L);

        assertNotNull(cremationQueue);


        em.remove(cremationQueue);

        em.flush();

        assertNull(em.find(CremationQueue.class,1L));
    }
}


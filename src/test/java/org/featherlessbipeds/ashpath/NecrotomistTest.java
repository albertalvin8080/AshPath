package org.featherlessbipeds.ashpath;

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
    public void persistindo_necrotomista()
    {
        //Esse tem ID 2
        Necrotomist necrotomist = new Necrotomist();
        necrotomist.setSpecialization("Autopsy");

        Deceased deceased = em.find(Deceased.class, 1L);
        necrotomist.addDeceased(deceased);

        CremationQueue cremationQueue = em.find(CremationQueue.class, 1L);
        necrotomist.addCremationQueue(cremationQueue);

        em.persist(necrotomist);
        em.flush();

        Necrotomist persistido = em.find(Necrotomist.class, 2L);

        assertNotNull(necrotomist.getId());
        assertNotNull(persistido);
        assertEquals("Autopsy", persistido.getSpecialization());
        assertEquals(1, persistido.getCremationQueueSet().size());
        assertEquals(1, persistido.getDeceasedSet().size());
    }

    @Test
    public void buscando_necrotomista()
    {

        Necrotomist necrotomist_buscado = em.find(Necrotomist.class, 1L);

        assertNotNull(necrotomist_buscado);
        // These two tests depend on the state of the database. They should be avoided.
//        assertEquals(2, necrotomist_buscado.getCremationQueueSet().size());
//        assertEquals(3, necrotomist_buscado.getDeceasedSet().size());

        necrotomist_buscado.getCremationQueueSet().forEach(queue ->
        {
            MatcherAssert.assertThat(queue.getId().toString(), CoreMatchers.anyOf(equalTo("1"), equalTo("2")));
        });

        necrotomist_buscado.getDeceasedSet().forEach(dec ->
        {
            MatcherAssert.assertThat(dec.getName(), CoreMatchers.anyOf(
                    equalTo("Neymar"),
                    equalTo("Josef Baumler"),
                    equalTo("Kroner Hass")
            ));
        });
    }
}

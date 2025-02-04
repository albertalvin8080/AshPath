package org.featherlessbipeds.ashpath._01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Grave;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DeceasedTest extends TestHelper
{
    @Test
    public void persistDeceased_AddsDeceasedToDb_WhenSuccessful()
    {
        Grave grave4 = new Grave();
        grave4.setLocation("Tokyo");
        em.persist(grave4);
        em.flush();

        Deceased deceased = new Deceased();
        deceased.setName("Satoru Mikomi");
        deceased.setCauseOfDeath("Stabbed while protecting a friend");
        deceased.setBirthDate(Timestamp.valueOf("1981-04-15 00:00:00"));
        deceased.setDeathDate(Timestamp.valueOf("2018-07-01 13:45:00"));

        Grave grave = em.find(Grave.class, 4L);
        assertNotNull(grave);
        deceased.setGrave(grave);

        CremationQueue cremationQueue = em.find(CremationQueue.class, 1L);
        assertNotNull(cremationQueue);
        deceased.setCremationQueue(cremationQueue);

        Necrotomist necrotomist = em.find(Necrotomist.class, 1L);
        assertNotNull(necrotomist);
        deceased.setNecrotomist(necrotomist);

        em.persist(deceased);
        em.flush();

        assertNotNull(deceased.getId());
    }

    @Test
    public void findDeceased_ReturnsDeceased_WhenSuccessful() throws ParseException
    {
        Deceased deceased = em.find(Deceased.class, 1L);
        assertNotNull(deceased);
        assertEquals("Helmuth Voss", deceased.getName());
        assertEquals("Heart Attack", deceased.getCauseOfDeath());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertEquals(sdf.parse("1960-05-15 00:00:00"), deceased.getBirthDate());
        assertEquals(sdf.parse("2023-10-01 12:00:00"), deceased.getDeathDate());

        assertNotNull(deceased.getGrave());
        assertEquals(Long.valueOf(1), deceased.getGrave().getId());

        assertEquals(Long.valueOf(1), deceased.getCremationQueue().getId());
        assertEquals(Long.valueOf(1), deceased.getNecrotomist().getId());
    }
    
    @Test // TEMPORARY
    public void testing_image() throws FileNotFoundException, IOException
    {
        Deceased d = em.find(Deceased.class, 4L);
        byte[] img = d.getDeceasedImage().getImg();
        try(FileOutputStream fos = new FileOutputStream(new File("test.jpg")))
        {
            fos.write(img);
            fos.flush();
        }
    }
}

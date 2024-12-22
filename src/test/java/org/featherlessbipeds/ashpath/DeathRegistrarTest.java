package org.featherlessbipeds.ashpath;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.DeathRegistrar;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import static org.junit.Assert.*;
import org.junit.Test;

public class DeathRegistrarTest extends TestHelper
{
    @Test
    public void persistDeathRegistrar_AddsEntityToDb_WhenSuccessful()
    {
        DeathRegistrar dr = new DeathRegistrar();
        dr.addContactNumber("00 0000-0000");
        dr.setUsername("Madoka");
        dr.setEmail("madoka@yahoo.com");
        dr.setFullName("Madoka Kaname");
        dr.setLastActivityDate(null);
        dr.setRegistrationDate(new Date());
        dr.setPasswordHash("kyuubey999");
        
        em.persist(dr);
        em.flush();
        
        assertNotNull(dr.getId());
    }
    
    @Test
    public void findDeathRegistrar_ReturnsEnity_WhenSuccessful() throws ParseException
    {
        DeathRegistrar dr = em.find(DeathRegistrar.class, 2L);
        assertNotNull(dr);
        dr.getContactNumbers().stream().forEach(number -> {
            MatcherAssert.assertThat(number, CoreMatchers.anyOf(
                    CoreMatchers.equalTo("123-456-7890"),
                    CoreMatchers.equalTo("+48 987 654 321")
            ));
        });
        assertEquals(dr.getEmail(), "jakubfarobec@gmail.com");
        assertEquals(dr.getFullName(), "Jakub Farobek");
        assertEquals(dr.getUsername(), "Jakub");
        assertEquals(dr.getPasswordHash(), "12345abcde");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertEquals(dr.getRegistrationDate(), dateFormat.parse("2023-01-15 10:00:00"));
        assertEquals(dr.getLastActivityDate(), dateFormat.parse("2023-11-20 15:45:00"));
    }
}

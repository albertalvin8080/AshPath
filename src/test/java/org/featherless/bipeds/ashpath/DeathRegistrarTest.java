package org.featherless.bipeds.ashpath;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.featherless.bipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.DeathRegistrar;
import static org.junit.Assert.*;
import org.junit.Test;

public class DeathRegistrarTest extends TestHelper
{
    @Test
    public void persistDeathRegistrar_AddsEntityToDb_WhenSuccessful()
    {
        DeathRegistrar dr = new DeathRegistrar();
        dr.setContactNumber("00 0000-0000");
        dr.setUsername("Madoka");
        dr.setEmail("madoka@yahoo.com");
        dr.setFullName("Madoka Kaname");
        dr.setLastActivityDate(null);
        dr.setRegistrationDate(new Date());
        dr.setPasswordHash("kyuubey999");
        
        em.persist(dr);
        em.flush();
        
        assertNotNull(dr.getId());
        
        DeathRegistrar persistedDr = em.find(DeathRegistrar.class, dr.getId());
        assertEquals(persistedDr.getContactNumber(), dr.getContactNumber());
        assertEquals(persistedDr.getUsername(), dr.getUsername());
        assertEquals(persistedDr.getEmail(), dr.getEmail());
        assertEquals(persistedDr.getFullName(), dr.getFullName());
        assertEquals(persistedDr.getLastActivityDate(), dr.getLastActivityDate());
        assertEquals(persistedDr.getRegistrationDate(), dr.getRegistrationDate());
        assertEquals(persistedDr.getPasswordHash(), dr.getPasswordHash());
    }
    
    @Test
    public void findDeathRegistrar_ReturnsEnity_WhenSuccessful() throws ParseException
    {
        DeathRegistrar dr = em.find(DeathRegistrar.class, 2L);
        assertNotNull(dr);
        assertEquals(dr.getContactNumber(), "123-456-7890");
        assertEquals(dr.getEmail(), "jakubfarobec@gmail.com");
        assertEquals(dr.getFullName(), "Jakub Farobek");
        assertEquals(dr.getUsername(), "Jakub");
        assertEquals(dr.getPasswordHash(), "12345abcde");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertEquals(dr.getRegistrationDate(), dateFormat.parse("2023-01-15 10:00:00"));
        assertEquals(dr.getLastActivityDate(), dateFormat.parse("2023-11-20 15:45:00"));
    }
}

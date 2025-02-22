package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.featherlessbipeds.ashpath.entity.DeathRegistrar;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import static org.junit.Assert.*;
import org.junit.Test;

public class DeathRegistrarJpqlTest extends GenericTest
{
    @Test
    public void deathRegistrar_RegisteredDateBetween()
    {
        Timestamp D1 = Timestamp.valueOf("2024-06-08 00:00:00");
        Timestamp D2 = Timestamp.valueOf("2026-06-08 00:00:00");
        
        TypedQuery<DeathRegistrar> query = em.createQuery("SELECT d FROM DeathRegistrar d WHERE d.registrationDate BETWEEN ?1 AND ?2", DeathRegistrar.class);
        query.setParameter(1, D1);
        query.setParameter(2, D2);
        List<DeathRegistrar> list = query.getResultList();
        
        list.forEach(dr -> assertTrue(
                dr.getRegistrationDate().compareTo(D1) >= 0 // After or equal to 2024-06-08
                &&
                dr.getRegistrationDate().compareTo(D2) <= 0 // Before or equal to 2026-06-08
        ));
        
        assertEquals(5, list.size());
    }
    
    @Test
    public void deathRegistrar_RegistrationDateBetween_NamedQuery()
    {
        Timestamp D1 = Timestamp.valueOf("2024-06-08 00:00:00");
        Timestamp D2 = Timestamp.valueOf("2026-06-08 00:00:00");
        
        TypedQuery<DeathRegistrar> query = em.createNamedQuery("DeathRegistrar.RegistrationDateBetween", DeathRegistrar.class);
        query.setParameter(1, D1);
        query.setParameter(2, D2);
        List<DeathRegistrar> list = query.getResultList();
        
        list.forEach(dr -> assertTrue(
                dr.getRegistrationDate().compareTo(D1) >= 0 // After or equal to 2024-06-08
                &&
                dr.getRegistrationDate().compareTo(D2) <= 0 // Before or equal to 2026-06-08
        ));
        
        assertEquals(5, list.size());
    }
    
    @Test
    public void deathRegistrar_MaxMinRegistrationDate()
    {
        Query query = em.createQuery("SELECT MAX(dr.registrationDate), MIN(dr.registrationDate) FROM DeathRegistrar dr");
        Object[] result = (Object[]) query.getSingleResult();
        
        assertEquals(Date.from(Timestamp.valueOf("2025-01-15 07:30:00").toInstant()), (Date) result[0]); // MAX
        assertEquals(Date.from(Timestamp.valueOf("2023-01-15 10:00:00").toInstant()), (Date) result[1]); // MIN
    }
    
    @Test
    public void deathRegistrar_EmailLikeYahoo() 
    {
        TypedQuery<DeathRegistrar> query = em.createQuery("SELECT d FROM DeathRegistrar d WHERE d.email LIKE :email", DeathRegistrar.class);
        query.setParameter("email", "%@yahoo%");
        var list = query.getResultList();
        
        list.forEach(dr -> assertTrue(dr.getEmail().contains("yahoo")));
        
        assertEquals(2, list.size());
    }
    
    @Test
    public void deathRegistrar_ContactNumbersNotEmpty() 
    {
        // WARNING: When the DR has more than one phone, we need the DISTINCT keyword in order to avoid fetching the same Deathregistrar object more than once.
        TypedQuery<DeathRegistrar> query = em.createQuery(
                "SELECT DISTINCT dr FROM DeathRegistrar dr JOIN FETCH dr.contactNumbers", 
                DeathRegistrar.class
        );
        var list = query.getResultList();
        
        list.forEach(dr -> assertFalse(dr.getContactNumbers().isEmpty()));
        list.forEach(dr -> System.out.println(dr.toString()));
        
        assertEquals(3, list.size());
    }
}

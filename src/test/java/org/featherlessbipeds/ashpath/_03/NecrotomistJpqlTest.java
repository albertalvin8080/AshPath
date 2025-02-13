package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.TypedQuery;
import org.featherlessbipeds.ashpath._01.*;
import java.sql.Timestamp;
import java.util.List;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.MatcherAssert;
import static org.junit.Assert.*;
import org.junit.Test;

public class NecrotomistJpqlTest extends GenericTest
{
    @Test
    public void find_necrotomist_by_name()
    {
        String name = "Oscar of Astora";
        TypedQuery<Necrotomist> query = em.createQuery("SELECT n FROM Necrotomist n WHERE n.name LIKE :name",Necrotomist.class);
        query.setParameter("name", name);
        Necrotomist necrotomist = query.getSingleResult();
        assertEquals(necrotomist.getName(),name);
    }
    
    @Test
    public void find_necrotomist_by_name_named_query()
    {
        String name = "Lautrec of Carim";
        TypedQuery<Necrotomist> query = em.createNamedQuery("Necrotomist.findByName",Necrotomist.class);
        query.setParameter("name", "Lautrec of Carim");   
        Necrotomist necrotomist = query.getSingleResult();
        assertEquals(necrotomist.getName(),name);      
    }
    
    @Test
    public void find_necrotomist_by_specialization()
    {
        String specialization = "Pathologist";
        TypedQuery<Necrotomist> query = em.createQuery("SELECT n FROM Necrotomist n WHERE n.specialization LIKE :specialization",Necrotomist.class);
        query.setParameter("specialization", specialization);
        Necrotomist necrotomist = query.getSingleResult();
        assertEquals(necrotomist.getSpecialization(),specialization);
    }
    
    @Test
    public void find_necrotomist_has_more_than_one_deseased()
    {
        String specialization = "Toxicologist";
        TypedQuery<Necrotomist> query = em.createQuery("SELECT n FROM Necrotomist n WHERE SIZE(n.deceasedSet) > 1 AND n.specialization= :specialization",Necrotomist.class);
        query.setParameter("specialization",specialization);
        Necrotomist necrotomist = query.getSingleResult();
        assertEquals(necrotomist.getName(),"Sirris of the Sunless Realms");
        assertEquals(necrotomist.getSpecialization(),specialization);
    }
    
    @Test
    public void shouldReturnNecrotomistWithAssociatedDeceased_whenDeceasedNameIsGiven(){
        String necroName = "Lautrec of Carim";
        String deceasedName = "Dusk of Oolacile";
        TypedQuery<Necrotomist> query = em.createQuery("SELECT n FROM Necrotomist n JOIN n.deceasedSet ds WHERE ds.name = :dsname",  Necrotomist.class);  
        query.setParameter("dsname", deceasedName);
        Necrotomist necrotomist = query.getSingleResult();
        assertEquals(necrotomist.getName(),necroName);
        assertEquals(necrotomist.getDeceasedSet().stream().findFirst().get().getName(),deceasedName );
    }
    
}

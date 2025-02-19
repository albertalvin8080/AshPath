package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.TypedQuery;
import java.util.List;
import org.featherlessbipeds.ashpath._01.*;
import org.featherlessbipeds.ashpath.entity.Grave;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GraveJpqlTest extends GenericTest
{

//Procurando um tumulo pelo nome do falecido
    @Test
public void grave_FindByDeceasedName() {
    TypedQuery<Grave> query = em.createQuery(
        "SELECT g FROM Grave g JOIN g.deceased d WHERE d.name LIKE :name", 
        Grave.class
    );
    query.setParameter("name", "Siegmeyer of Catarina");
    List<Grave> graveList = query.getResultList();

    assertEquals(1, graveList.size());
    assertEquals("Anor Londo", graveList.get(0).getLocation());
}

//Contar numero de tumulos ocupados de acordo com Deceased
@Test
public void grave_CountEmptyGraves() {
    TypedQuery<Long> query = em.createQuery(
        "SELECT COUNT(g) FROM Grave g WHERE g.deceased IS NOT NULL", 
        Long.class
    );
    Long count = query.getSingleResult();

    assertEquals(12L, count.longValue());
}

//Contar numero de Graves com location em Maceio
@Test
public void count_GravesInMaceio() {
    TypedQuery<Long> query = em.createQuery(
        "SELECT COUNT(g) FROM Grave g WHERE g.location = :location", 
        Long.class
    );
    query.setParameter("location", "Maceio");
    
    Long count = query.getSingleResult();

    assertEquals(2L, count.longValue());
}

//Verificando o nome das Locations que o Necrotomist do Deceased se chama Mohammed
@Test
public void gravesPorNecrotomistaNomeNamedQuery() {
    
    // Usando a NamedQuery para buscar o necrotomista por nome
    TypedQuery<Necrotomist> query = em.createNamedQuery("Necrotomist.findByName", Necrotomist.class);
    query.setParameter("name", "Mohammed");

    // Obter o necrotomista
    Necrotomist necrotomist = query.getSingleResult();

    // Buscar as localizações das Grave associadas ao necrotomista
    TypedQuery<String> locationQuery = em.createQuery(
        "SELECT g.location FROM Grave g " +
        "JOIN g.deceased d " +
        "WHERE d.necrotomist = :necrotomist", 
        String.class
    );
    locationQuery.setParameter("necrotomist", necrotomist);
    
    List<String> graveLocations = locationQuery.getResultList();

    // Verificando se as location são as esperadas
    graveLocations.forEach(location -> {
        assertTrue(location.equals("Nosramus") || location.equals("Berlin"));
    });

    // Verificando o tamanho da lista
    assertEquals(2, graveLocations.size());
}

}

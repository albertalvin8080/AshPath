package org.featherlessbipeds.ashpath.first_task;

import org.featherlessbipeds.ashpath.entity.Grave;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;


public class GraveTest extends TestHelper {
    @Test
    public void persistGrave_AddsEntityToDb_WhenSuccessful() {
        // Criando uma nova instância de Grave
        Grave grave = new Grave();
        grave.setLocation("Row12, Plot 5");

        // Persistindo a instância no banco
        em.persist(grave);
        em.flush();

        // Verificando se o ID foi gerado corretamente
        assertNotNull(grave.getId());
    }

    @Test
    public void findGrave_ReturnsEntity_WhenSuccessful() {
        // Buscando uma instância já existente no banco de dados
        Grave grave = em.find(Grave.class, 1L); // ID previamente conhecido do dataset
        assertNotNull(grave);

        // Verificando se os dados persistidos são os esperados
        assertEquals(grave.getLocation(), "Frankfurt"); // Valor do dataset XML
    }
}

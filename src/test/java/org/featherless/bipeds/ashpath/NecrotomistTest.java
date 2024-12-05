/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.featherless.bipeds.ashpath;

import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Samsung
 */


public class NecrotomistTest extends TestHelper{

@Test
public void persistindo_necrotomista(){
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
    assertEquals(1,persistido.getCremationQueueSet().size());
    assertEquals(1,persistido.getDeceasedSet().size());
}
@Test
public void buscando_necrotomista(){
    
    Necrotomist necrotomist_buscado = em.find(Necrotomist.class, 1L);
    
    assertNotNull(necrotomist_buscado); 
    assertEquals(1,necrotomist_buscado.getCremationQueueSet().size());
    assertEquals(1,necrotomist_buscado.getDeceasedSet().size());
 


    
    
    
}

}

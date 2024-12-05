/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.featherless.bipeds.ashpath;

import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.entity.Deceased;
import org.featherlessbipeds.ashpath.entity.Necrotomist;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Samsung
 */


public class NecrotomistTest extends TestHelper{

@Test
public void persistirNecrotomista(){
    
    Necrotomist necrotomist = new Necrotomist();
    necrotomist.setSpecialization("Autopsy");
    
    Deceased deceased = em.find(Deceased.class, 1L);
    necrotomist.addDeceased(deceased);
    CremationQueue cremationQueue = em.find(CremationQueue.class, 1L);
    necrotomist.addCremationQueue(cremationQueue);
    
    em.persist(necrotomist);
    em.flush();
    
    assertNotNull(necrotomist.getId());
               
    
    
}
    
    
    
}

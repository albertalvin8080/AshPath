package org.featherless.bipeds.ashpath;

import org.featherlessbipeds.ashpath.entity.Admin;
import org.featherlessbipeds.ashpath.entity.AdminRole;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest extends TestHelper
{
    @Test
    public void persist_admin()
    {
        Admin adm = new Admin();
        adm.setRole(AdminRole.SYSTEM_ADMIN);
        adm.setPasswordHash("12345");
        adm.setUsername("Franz Bonaparta");
        
        em.persist(adm);
        // Why the hell is this necessary? Shoundn't it be fetching from the persistence context?
        em.flush(); 
        
        assertNotNull(adm.getId());
        Admin persisted = em.find(Admin.class, 1L);
        
        System.out.println(persisted);
        assertNotNull(persisted);
    }

    @Test
    public void fetch_admin()
    {

    }
}

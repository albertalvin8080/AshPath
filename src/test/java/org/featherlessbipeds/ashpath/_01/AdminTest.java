package org.featherlessbipeds.ashpath._01;

import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.Admin;
import org.featherlessbipeds.ashpath.entity.AdminRole;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest extends TestHelper
{
    @Test
    public void persistAdmin_AddsAdminToDb_WhenSuccessful()
    {
        Admin adm = new Admin();
        adm.setRole(AdminRole.SYSTEM_ADMIN);
        adm.setPassword("P@ssword123");
        adm.setUsername("FranzBonaparta");

        em.persist(adm);
        em.flush();

        assertNotNull(adm.getId());
    }

    @Test
    public void findAdmin_ReturnsAdmin_WhenSuccessful()
    {
        Admin adm = em.find(Admin.class, 3L);
        assertNotNull(adm);
        assertEquals(adm.getPassword(), "Hash%66hash");
        assertEquals(adm.getRole(), AdminRole.USER_ADMIN);
        assertEquals(adm.getUsername(), "mononoke");
    }

    @Test
    public void findAdmin_ReturnsNull_WhenNoAdminWasFound()
    {
        Admin adm = em.find(Admin.class, 2L);
        assertNull(adm);
    }
}

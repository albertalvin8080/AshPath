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
        adm.setPasswordHash("12345");
        adm.setUsername("Franz Bonaparta");

        em.persist(adm);
        // Why the hell is this necessary? Shoundn't it be fetching from the persistence context?
        em.flush();

        assertNotNull(adm.getId());
    }

    @Test
    public void findAdmin_ReturnsAdmin_WhenSuccessful()
    {
        Admin adm = em.find(Admin.class, 3L);
        assertNotNull(adm);
        assertEquals(adm.getPasswordHash(), "hashhashhash");
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

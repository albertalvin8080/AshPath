package org.featherlessbipeds.ashpath.second_task;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import org.featherlessbipeds.ashpath.utils.TestHelper;
import org.featherlessbipeds.ashpath.entity.Admin;
import org.featherlessbipeds.ashpath.entity.AdminRole;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest extends TestHelper
{
    @Test
    public void updateAdmin()
    {
        var newRole = AdminRole.USER_ADMIN;
        var newUsername = "Strokovsky";
        var newPasswordHash = "AAAAA";
        
        Long id = 7L;
        Admin adm = em.find(Admin.class, id);
        adm.setRole(newRole);
        adm.setUsername(newUsername);
        adm.setPasswordHash(newPasswordHash);
        
        em.flush();
        
        String jpql = "SELECT a FROM Admin a WHERE a.id = ?1";
        TypedQuery<Admin> query = em.createQuery(jpql, Admin.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        adm = query.getSingleResult();
        
        assertEquals(adm.getRole(), newRole);
        assertEquals(adm.getUsername(), newUsername);
        assertEquals(adm.getPasswordHash(), newPasswordHash);
    }

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void updateAdminMerge()
    {
        var newRole = AdminRole.CONTENT_ADMIN;
        var newUsername = "Rinkovsky";
        var newPasswordHash = "BBBBB";
        
        Long id = 7L;
        Admin adm = em.find(Admin.class, id);
        adm.setRole(newRole);
        adm.setUsername(newUsername);
        adm.setPasswordHash(newPasswordHash);
        
        em.clear();
        adm = (Admin) em.merge(adm);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        adm = em.find(Admin.class, id, properties);
        
        assertEquals(adm.getRole(), newRole);
        assertEquals(adm.getUsername(), newUsername);
        assertEquals(adm.getPasswordHash(), newPasswordHash);
    }

    @Test
    public void removeAdmin()
    {
        Long id = 4L;
        Admin adm = em.find(Admin.class, id);
        em.remove(adm);
        adm = em.find(Admin.class, id);
        assertNull(adm);
    }
}

package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.function.Function;
import org.featherlessbipeds.ashpath.entity.Admin;
import org.featherlessbipeds.ashpath.entity.AdminRole;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminJpqlTest extends GenericTest
{
    @Test
    public void admin_RoleInSsystemOrUser()
    {
        TypedQuery<Admin> query = em.createQuery("SELECT a FROM Admin a WHERE a.role IN (:role1, :role2)", Admin.class);
        query.setParameter("role1", AdminRole.SYSTEM_ADMIN);
        query.setParameter("role2", AdminRole.USER_ADMIN);
        List<Admin> list = query.getResultList();

        list.forEach(a -> MatcherAssert.assertThat(
                a.getRole(),
                CoreMatchers.anyOf(
                        CoreMatchers.equalTo(AdminRole.SYSTEM_ADMIN),
                        CoreMatchers.equalTo(AdminRole.USER_ADMIN)
                )
        ));

        assertEquals(7, list.size());
    }

    @Test
    public void admin_RoleInSsystemOrUser_NamedQuery()
    {
        TypedQuery<Admin> query = em.createNamedQuery("Admin.InSystemUser", Admin.class);
        query.setParameter("role1", AdminRole.SYSTEM_ADMIN);
        query.setParameter("role2", AdminRole.USER_ADMIN);
        List<Admin> list = query.getResultList();

        list.forEach(a -> MatcherAssert.assertThat(
                a.getRole(),
                CoreMatchers.anyOf(
                        CoreMatchers.equalTo(AdminRole.SYSTEM_ADMIN),
                        CoreMatchers.equalTo(AdminRole.USER_ADMIN)
                )
        ));

        assertEquals(7, list.size());
    }

    @Test
    public void admin_CountContentAdmin()
    {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(a) FROM Admin a WHERE a.role = :role", Long.class);
        query.setParameter("role", AdminRole.CONTENT_ADMIN);
        Long r = query.getSingleResult();

        assertEquals(1L, r.longValue());
    }

    @Test
    public void admin_CountGroupByRoleOrderByRole()
    {
        Query query = em.createQuery("SELECT a.role, COUNT(a) FROM Admin a GROUP BY a.role ORDER BY a.role");
        List<Object[]> list = query.getResultList();

        Function<Object[], String> extract = (o) ->
        {
            return String.format("%s:%d", (AdminRole) o[0], (long) o[1]);
        };

        assertEquals("CONTENT_ADMIN:1", extract.apply(list.get(0)));
        assertEquals("SYSTEM_ADMIN:4", extract.apply(list.get(1)));
        assertEquals("USER_ADMIN:3", extract.apply(list.get(2)));
    }
}

package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.TypedQuery;
import org.featherlessbipeds.ashpath.entity.User;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class UserJpqlTest extends GenericTest
{
    @Test
    public void user_UsernameLikeA()
    {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username LIKE :username", User.class);
        query.setParameter("username", "A%");
        var list = query.getResultList();
        
//        list.forEach(u -> System.out.println(u.getUsername()));
        list.forEach(u -> assertTrue(u.getUsername().startsWith("A")));
        
        assertEquals(2, list.size());
    }
    
    @Test
    public void user_UserCount()
    {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u", Long.class);
        var r = query.getSingleResult();
        
        assertEquals(16L, r.longValue());
    }
}

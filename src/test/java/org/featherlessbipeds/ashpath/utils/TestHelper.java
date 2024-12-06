package org.featherlessbipeds.ashpath.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class TestHelper
{
    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;

    @BeforeClass
    public static void setUpClass()
    {
        emf = Persistence.createEntityManagerFactory("ashpath");
       DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass()
    {
        emf.close();
    }

    @Before
    public void setUp()
    {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown()
    {
        if (!et.getRollbackOnly())
        {
            et.commit();
        }
        em.close();
    }
}

package org.featherlessbipeds.ashpath._03;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.featherlessbipeds.ashpath.entity.CremationQueue;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import static org.junit.Assert.*;
import org.junit.Test;

public class CremationQueueJpqlTest extends GenericTest {

    @Test
    public void cremationQueue_FindByEnteredDate() {
        Timestamp date = Timestamp.valueOf("2025-02-01 14:00:00");

        TypedQuery<CremationQueue> query = em.createQuery(
                "SELECT cq FROM CremationQueue cq WHERE cq.enteredDate = :enteredDate",
                CremationQueue.class
        );
        query.setParameter("enteredDate", date);
        List<CremationQueue> list = query.getResultList();

        list.forEach(cq -> assertEquals(date.getTime(), cq.getEnteredDate().getTime()));

        assertEquals(1, list.size()); // Atualizado para refletir o dataset
    }

    @Test
    public void cremationQueue_FindByEnteredDate_NamedQuery() {
        Timestamp date = Timestamp.valueOf("2025-02-01 14:00:00");

        TypedQuery<CremationQueue> query = em.createNamedQuery("CremationQueue.FindByEnteredDate", CremationQueue.class);
        query.setParameter("enteredDate", date);
        List<CremationQueue> list = query.getResultList();

        list.forEach(cq -> assertEquals(date.getTime(), cq.getEnteredDate().getTime()));

        assertEquals(1, list.size());
    }

    @Test
    public void cremationQueue_MaxMinEnteredDate() {
        Query query = em.createQuery("SELECT MAX(cq.enteredDate), MIN(cq.enteredDate) FROM CremationQueue cq");
        Object[] result = (Object[]) query.getSingleResult();

        // As datas podem ser retornadas como java.util.Date, então utilizamos diretamente elas.
        Date maxDate = (Date) result[0]; // MAX
        Date minDate = (Date) result[1]; // MIN

        // Convertendo para Timestamp para comparação com as expectativas
        Timestamp expectedMax = Timestamp.valueOf("2025-02-07 00:00:00");
        Timestamp expectedMin = Timestamp.valueOf("2023-10-01 12:00:00");

        assertEquals(expectedMax, new Timestamp(maxDate.getTime())); // MAX
        assertEquals(expectedMin, new Timestamp(minDate.getTime())); // MIN
    }


    @Test
    public void cremationQueue_EmptyDeceasedSet() {
        TypedQuery<CremationQueue> query = em.createQuery(
                "SELECT cq FROM CremationQueue cq WHERE SIZE(cq.deceasedSet) = 0",
                CremationQueue.class
        );
        var list = query.getResultList();

        list.forEach(cq -> assertTrue(cq.getDeceasedSet().isEmpty()));

        assertEquals(1, list.size()); // Supondo que 2 filas estejam vazias
    }
}
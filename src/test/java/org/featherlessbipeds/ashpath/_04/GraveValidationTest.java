package org.featherlessbipeds.ashpath._04;

import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.featherlessbipeds.ashpath.entity.Grave;
import org.featherlessbipeds.ashpath.utils.GenericTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraveValidationTest extends GenericTest {

    private Validator validator;

    @Before
    public void setUpValidator() {
        // Inicializa o Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGraveLocationNotNull() {
        Grave grave = new Grave();
        grave.setLocation(null);

        var violations = validator.validate(grave);
        assertFalse(violations.isEmpty());
        assertEquals("A localização do túmulo não pode ser nula.", violations.iterator().next().getMessage());
    }

    @Test
    public void testGraveLocationBigSize() {
        Grave grave = new Grave();
        grave.setLocation("Esta localização tem mais de trinta caracteres, o que é inválido.");

        var violations = validator.validate(grave);
        assertFalse(violations.isEmpty());
        assertEquals("A localização do túmulo deve ter entre 1 e 30 caracteres.", violations.iterator().next().getMessage());
    }

    @Test
    public void updateLocationWithFloatError() {
        // Busca um Grave existente no banco de dados
        TypedQuery<Grave> query = em.createQuery(
                "SELECT g FROM Grave g WHERE g.location = :location", Grave.class);
        query.setParameter("location", "Anor Londo");
        Grave grave = query.getSingleResult();

        // Atualiza a localização para um valor inválido (contém números decimais)
        grave.setLocation("Anor Londo 123.45");

        try {
            // Tenta persistir a alteração
            em.flush();
            fail("Deveria ter lançado uma ConstraintViolationException"); // Se não lançar exceção, o teste falha
        } catch (ConstraintViolationException ex) {
            // Captura a exceção e verifica a mensagem de erro e o número de violações
            ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
            assertEquals("A localização do túmulo não pode conter números decimais.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
        }
    }

    @Test
    public void testValidGrave() {
        Grave grave = new Grave();
        grave.setLocation("Localização válida");

        var violations = validator.validate(grave);
        assertTrue(violations.isEmpty());
    }
}

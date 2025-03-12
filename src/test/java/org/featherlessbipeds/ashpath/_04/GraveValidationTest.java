package org.featherlessbipeds.ashpath._04;

import org.featherlessbipeds.ashpath.utils.TestHelper;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.featherlessbipeds.ashpath.entity.Grave;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraveValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
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
    public void testGraveLocationSize() {
        Grave grave = new Grave();
        grave.setLocation("Esta localização tem mais de trinta caracteres, o que é inválido.");

        var violations = validator.validate(grave);
        assertFalse(violations.isEmpty());
        assertEquals("A localização do túmulo deve ter entre 1 e 30 caracteres.", violations.iterator().next().getMessage());
    }

    @Test
    public void testGraveLocationNoFloat() {
        Grave grave = new Grave();
        grave.setLocation("Local 123.45");

        var violations = validator.validate(grave);
        assertFalse(violations.isEmpty());
        assertEquals("A localização do túmulo não pode conter números decimais.", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidGrave() {
        Grave grave = new Grave();
        grave.setLocation("Localização válida");

        var violations = validator.validate(grave);
        assertTrue(violations.isEmpty());
    }
}
package cl.duoc.app.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class UtilProductoTest {

    @Test
    void testIsEmptyOrNullWithNull() {
        assertTrue(UtilProducto.isEmptyOrNull(null), "Debería retornar true si el string es null");
    }

    @Test
    void testIsEmptyOrNullWithEmptyString() {
        assertTrue(UtilProducto.isEmptyOrNull(""), "Debería retornar true si el string está vacío");
    }

    @Test
    void testIsEmptyOrNullWithNonEmptyString() {
        assertFalse(UtilProducto.isEmptyOrNull("abc"), "Debería retornar false si el string no está vacío");
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<UtilProducto> constructor = UtilProducto.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "El constructor debe ser privado");
        constructor.setAccessible(true);
        UtilProducto instance = constructor.newInstance();
        assertNotNull(instance);
    }
}

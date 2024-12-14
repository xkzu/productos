package cl.duoc.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Producto producto = new Producto();
        producto.setId(Long.valueOf(1L));
        producto.setNombre("Televisor");
        producto.setPrecio("99999");
        producto.setMarca("Sony");
        producto.setDescripcion("Televisor 4K");
        producto.setImagen("televisor.png");

        assertEquals(1L, producto.getId());
        assertEquals("Televisor", producto.getNombre());
        assertEquals("99999", producto.getPrecio());
        assertEquals("Sony", producto.getMarca());
        assertEquals("Televisor 4K", producto.getDescripcion());
        assertEquals("televisor.png", producto.getImagen());

        String toString = producto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Televisor"));
    }

    @Test
    void testAllArgsConstructor() {
        Producto producto = new Producto(
                Long.valueOf(2L),
                "Laptop",
                "1500000",
                "Dell",
                "Laptop gamer",
                "laptop.png"
        );

        assertEquals(2L, producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals("1500000", producto.getPrecio());
        assertEquals("Dell", producto.getMarca());
        assertEquals("Laptop gamer", producto.getDescripcion());
        assertEquals("laptop.png", producto.getImagen());
    }

    @Test
    void testEqualsAndHashCode() {
        Producto p1 = new Producto(Long.valueOf(3L), "Smartphone", "500000", "Samsung", "Smartphone 5G", "phone.png");
        Producto p2 = new Producto(Long.valueOf(3L), "Smartphone", "500000", "Samsung", "Smartphone 5G", "phone.png");
        Producto p3 = new Producto(Long.valueOf(4L), "Tablet", "300000", "Apple", "Tablet Retina", "tablet.png");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testEqualsSameObject() {
        Producto p = new Producto();
        p.setId(Long.valueOf(1L));
        p.setNombre("Producto");
        p.setPrecio("1000");
        p.setMarca("Marca");
        p.setDescripcion("Desc");
        p.setImagen("img.png");

        // Comparación con el mismo objeto debe ser true
        assertEquals(p, p, "Un objeto debe ser igual a sí mismo");
    }

    @Test
    void testEqualsNull() {
        Producto p = new Producto();
        p.setId(Long.valueOf(1L));

        // Comparación con null debe ser false
        assertNotEquals(null, p, "Un objeto no debe ser igual a null");
    }

    @Test
    void testEqualsDifferentClass() {
        Producto p = new Producto();
        p.setId(Long.valueOf(1L));

        String otroObjeto = "No es un Producto";

        // Comparación con objeto de otra clase debe ser false
        assertNotEquals(p, otroObjeto, "Un objeto no debe ser igual a un tipo distinto");
    }

    @Test
    void testEqualsDifferentId() {
        Producto p1 = new Producto();
        p1.setId(Long.valueOf(1L));
        p1.setNombre("Producto");
        p1.setPrecio("1000");
        p1.setMarca("Marca");
        p1.setDescripcion("Desc");
        p1.setImagen("img.png");

        Producto p2 = new Producto();
        p2.setId(Long.valueOf(2L));
        p2.setNombre("Producto");
        p2.setPrecio("1000");
        p2.setMarca("Marca");
        p2.setDescripcion("Desc");
        p2.setImagen("img.png");

        // Diferencia en el id debe dar false
        assertNotEquals(p1, p2, "Productos con distinto id no deben ser iguales");
    }

    @Test
    void testEqualsAllFieldsEqual() {
        Producto p1 = new Producto();
        p1.setId(Long.valueOf(1L));
        p1.setNombre("Producto");
        p1.setPrecio("1000");
        p1.setMarca("Marca");
        p1.setDescripcion("Desc");
        p1.setImagen("img.png");

        Producto p2 = new Producto();
        p2.setId(Long.valueOf(1L));
        p2.setNombre("Producto");
        p2.setPrecio("1000");
        p2.setMarca("Marca");
        p2.setDescripcion("Desc");
        p2.setImagen("img.png");

        // Todos los campos iguales debe ser true
        assertEquals(p1, p2, "Productos con todos los campos iguales deben ser iguales");
    }

    @Test
    void testEqualsWithNullFields() {
        Producto p1 = new Producto();
        p1.setId(null);
        p1.setNombre(null);
        p1.setPrecio(null);
        p1.setMarca(null);
        p1.setDescripcion(null);
        p1.setImagen(null);

        Producto p2 = new Producto();
        p2.setId(null);
        p2.setNombre(null);
        p2.setPrecio(null);
        p2.setMarca(null);
        p2.setDescripcion(null);
        p2.setImagen(null);

        // Dos objetos con todos los campos null deben ser iguales
        assertEquals(p1, p2, "Objetos con todos los campos null deben ser iguales");

        // Cambiamos un campo en p2
        p2.setNombre("Diferente");
        // Ahora deben ser distintos
        assertNotEquals(p1, p2, "Objetos con un campo diferente no deben ser iguales");
    }
}

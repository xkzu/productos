package cl.duoc.app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoDTOTest {

    @Test
    void testGettersSetters() {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(Long.valueOf(1L));
        dto.setNombre("Producto");
        dto.setPrecio("1000");
        dto.setMarca("Marca");
        dto.setDescripcion("Desc");
        dto.setImagen("img.png");

        assertEquals(1L, dto.getId());
        assertEquals("Producto", dto.getNombre());
        assertEquals("1000", dto.getPrecio());
        assertEquals("Marca", dto.getMarca());
        assertEquals("Desc", dto.getDescripcion());
        assertEquals("img.png", dto.getImagen());
    }

    @Test
    void testEqualsSameObject() {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(Long.valueOf(1L));
        assertEquals(dto, dto, "El objeto debe ser igual a sí mismo");
    }

    @Test
    void testEqualsNull() {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(Long.valueOf(1L));
        assertNotEquals(null, dto, "Un objeto no debe ser igual a null");
    }

    @Test
    void testEqualsDifferentClass() {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(Long.valueOf(1L));
        String other = "Otra clase";
        assertNotEquals(dto, other, "Un objeto no debe ser igual a un tipo distinto");
    }

    @Test
    void testEqualsAllFieldsEqual() {
        ProductoDTO dto1 = new ProductoDTO();
        dto1.setId(Long.valueOf(1L));
        dto1.setNombre("Producto");
        dto1.setPrecio("1000");
        dto1.setMarca("Marca");
        dto1.setDescripcion("Desc");
        dto1.setImagen("img.png");

        ProductoDTO dto2 = new ProductoDTO();
        dto2.setId(Long.valueOf(1L));
        dto2.setNombre("Producto");
        dto2.setPrecio("1000");
        dto2.setMarca("Marca");
        dto2.setDescripcion("Desc");
        dto2.setImagen("img.png");

        assertEquals(dto1, dto2, "Dos objetos con mismos valores deben ser iguales");
    }

    @Test
    void testEqualsDifferentId() {
        ProductoDTO dto1 = new ProductoDTO();
        dto1.setId(Long.valueOf(1L));
        dto1.setNombre("Producto");

        ProductoDTO dto2 = new ProductoDTO();
        dto2.setId(Long.valueOf(2L));
        dto2.setNombre("Producto");

        assertNotEquals(dto1, dto2, "Deben ser distintos si tienen distinto id");
    }

    @Test
    void testEqualsWithNullFields() {
        ProductoDTO dto1 = new ProductoDTO();
        dto1.setId(null);
        dto1.setNombre(null);

        ProductoDTO dto2 = new ProductoDTO();
        dto2.setId(null);
        dto2.setNombre(null);

        // Aquí ambos objetos son iguales (todos los campos nulos)
        assertEquals(dto1, dto2, "Ambos objetos con campos nulos deben ser iguales");

        // Cambiamos un campo en dto2
        dto2.setNombre("Diferente");

        // Ahora deben ser distintos, ya que dto2 tiene un campo diferente
        assertNotEquals(dto1, dto2, "Si uno tiene un campo diferente, ya no deben ser iguales");
    }

    @Test
    void testHashCode() {
        ProductoDTO dto1 = new ProductoDTO();
        dto1.setId(Long.valueOf(1L));
        dto1.setNombre("Producto");

        ProductoDTO dto2 = new ProductoDTO();
        dto2.setId(Long.valueOf(1L));
        dto2.setNombre("Producto");

        // Igual hash si son objetos iguales
        assertEquals(dto1.hashCode(), dto2.hashCode(), "Objetos iguales deben tener el mismo hash");

        dto2.setId(Long.valueOf(2L));
        // Distinto hash si hay un campo distinto
        assertNotEquals(dto1.hashCode(), dto2.hashCode(), "Objetos distintos deben tener hash distinto");
    }

    @Test
    void testToString() {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(Long.valueOf(1L));
        dto.setNombre("Televisor");
        dto.setPrecio("99999");
        dto.setMarca("Sony");
        dto.setDescripcion("Televisor 4K UHD");
        dto.setImagen("tv.png");

        String result = dto.toString();
        assertNotNull(result, "toString() no debe retornar null");
        assertTrue(result.contains("Televisor"), "toString() debe contener el nombre");
        assertTrue(result.contains("99999"), "toString() debe contener el precio");
        assertTrue(result.contains("Sony"), "toString() debe contener la marca");
        assertTrue(result.contains("Televisor 4K UHD"), "toString() debe contener la descripción");
        assertTrue(result.contains("tv.png"), "toString() debe contener la imagen");
    }
}

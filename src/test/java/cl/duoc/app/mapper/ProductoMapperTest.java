package cl.duoc.app.mapper;

import cl.duoc.app.dto.ProductoDTO;
import cl.duoc.app.model.Producto;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ProductoMapperTest {

    @Test
    void testToDTO() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setPrecio("1000");
        producto.setMarca("Marca Test");
        producto.setDescripcion("Descripción Test");
        producto.setImagen("imagen.png");

        ProductoDTO dto = ProductoMapper.toDTO(producto);

        assertEquals(1L, dto.getId());
        assertEquals("Producto Test", dto.getNombre());
        assertEquals("1000", dto.getPrecio());
        assertEquals("Marca Test", dto.getMarca());
        assertEquals("Descripción Test", dto.getDescripcion());
        assertEquals("imagen.png", dto.getImagen());
    }

    @Test
    void testToEntity() {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(2L);
        dto.setNombre("Laptop");
        dto.setPrecio("1500");
        dto.setMarca("Dell");
        dto.setDescripcion("Laptop de alta gama");
        dto.setImagen("laptop.png");

        Producto producto = ProductoMapper.toEntity(dto);

        assertEquals(2L, producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals("1500", producto.getPrecio());
        assertEquals("Dell", producto.getMarca());
        assertEquals("Laptop de alta gama", producto.getDescripcion());
        assertEquals("laptop.png", producto.getImagen());
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<ProductoMapper> constructor = ProductoMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        ProductoMapper instance = constructor.newInstance();
        assertNotNull(instance);
    }
}

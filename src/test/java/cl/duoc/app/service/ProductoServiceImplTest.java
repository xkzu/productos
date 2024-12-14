package cl.duoc.app.service;

import cl.duoc.app.model.Producto;
import cl.duoc.app.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoServiceImpl service;

    @Test
    void testFindAll() {
        List<Producto> productosMock = Arrays.asList(new Producto(), new Producto());
        when(repository.findAll()).thenReturn(productosMock);

        List<Producto> result = service.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByIdFound() {
        Producto producto = new Producto();
        producto.setId(Long.valueOf(1L));
        when(repository.findById(Long.valueOf(1L))).thenReturn(Optional.of(producto));

        Optional<Producto> result = service.findById(Long.valueOf(1L));
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repository, times(1)).findById(Long.valueOf(1L));
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(Long.valueOf(2L))).thenReturn(Optional.empty());

        Optional<Producto> result = service.findById(Long.valueOf(2L));
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(Long.valueOf(2L));
    }

    @Test
    void testSave() {
        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        when(repository.save(producto)).thenReturn(producto);

        Producto result = service.save(producto);
        assertNotNull(result);
        assertEquals("Producto Test", result.getNombre());
        verify(repository, times(1)).save(producto);
    }

    @Test
    void testDelete() {
        service.delete(Long.valueOf(3L));
        verify(repository, times(1)).deleteById(Long.valueOf(3L));
    }
}

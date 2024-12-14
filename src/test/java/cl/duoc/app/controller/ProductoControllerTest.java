package cl.duoc.app.controller;

import cl.duoc.app.dto.ProductoDTO;
import cl.duoc.app.model.Producto;
import cl.duoc.app.service.ProductoService;
import cl.duoc.app.mapper.ProductoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    private ObjectMapper objectMapper;
    private Producto producto;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setMarca("Marca Test");
        producto.setPrecio("1000");
        producto.setDescripcion("Descripción Test");
        producto.setImagen("imagen.png");

        productoDTO = ProductoMapper.toDTO(producto);
    }

    @Test
    void testListar() throws Exception {
        Producto p2 = new Producto();
        p2.setId(2L);
        p2.setNombre("Otro Producto");
        p2.setMarca("Otra Marca");
        p2.setPrecio("2000");
        p2.setDescripcion("Otra desc");
        p2.setImagen("otra.png");

        List<Producto> productos = Arrays.asList(producto, p2);
        when(productoService.findAll()).thenReturn(productos);

        mockMvc.perform(get("/productos/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Producto Test"))
                .andExpect(jsonPath("$[1].nombre").value("Otro Producto"));

        verify(productoService, times(1)).findAll();
    }

    @Test
    void testListarInternalServerError() throws Exception {
        when(productoService.findAll()).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/productos/listar"))
                .andExpect(status().isInternalServerError());

        verify(productoService, times(1)).findAll();
    }

    @Test
    void testBuscarFound() throws Exception {
        when(productoService.findById(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/productos/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto Test"));

        verify(productoService, times(1)).findById(1L);
    }

    @Test
    void testBuscarNotFound() throws Exception {
        when(productoService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/productos/buscar/999"))
                .andExpect(status().isNotFound());

        verify(productoService, times(1)).findById(999L);
    }

    @Test
    void testBuscarBadRequest() throws Exception {
        // Caso id <= 0
        mockMvc.perform(get("/productos/buscar/-1"))
                .andExpect(status().isBadRequest());
        verify(productoService, never()).findById(anyLong());
    }

    @Test
    void testBuscarBadRequestNull() throws Exception {
        mockMvc.perform(get("/productos/buscar/null"))
                .andExpect(status().isBadRequest());
        verify(productoService, never()).findById(anyLong());
    }

    @Test
    void testBuscarInternalServerError() throws Exception {
        when(productoService.findById(5L)).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(get("/productos/buscar/5"))
                .andExpect(status().isInternalServerError());

        verify(productoService, times(1)).findById(5L);
    }

    @Test
    void testAgregarSuccess() throws Exception {
        Producto pGuardado = new Producto();
        pGuardado.setId(10L);
        pGuardado.setNombre("Producto Test");
        pGuardado.setMarca("Marca Test");
        pGuardado.setPrecio("1000");
        pGuardado.setDescripcion("Descripción Test");
        pGuardado.setImagen("imagen.png");

        when(productoService.save(any(Producto.class))).thenReturn(pGuardado);

        mockMvc.perform(post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nombre").value("Producto Test"));

        verify(productoService, times(1)).save(any(Producto.class));
    }

    @Test
    void testAgregarBadRequestNombre() throws Exception {
        ProductoDTO invalidDTO = new ProductoDTO();
        invalidDTO.setNombre(null);
        invalidDTO.setMarca("Marca X");
        invalidDTO.setPrecio("500");
        invalidDTO.setDescripcion("Desc");
        invalidDTO.setImagen("img.png");

        mockMvc.perform(post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    void testAgregarBadRequestMarca() throws Exception {
        ProductoDTO invalidDTO = new ProductoDTO();
        invalidDTO.setNombre("Nombre Valido");
        invalidDTO.setMarca("");
        invalidDTO.setPrecio("500");
        invalidDTO.setDescripcion("Desc");
        invalidDTO.setImagen("img.png");

        mockMvc.perform(post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    void testAgregarBadRequestPrecio() throws Exception {
        ProductoDTO invalidDTO = new ProductoDTO();
        invalidDTO.setNombre("Nombre Valido");
        invalidDTO.setMarca("Marca Valida");
        invalidDTO.setPrecio(null); // Falta el precio
        invalidDTO.setDescripcion("Desc");
        invalidDTO.setImagen("img.png");

        mockMvc.perform(post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    void testAgregarBadRequestDescripcion() throws Exception {
        ProductoDTO invalidDTO = new ProductoDTO();
        invalidDTO.setNombre("Nombre Valido");
        invalidDTO.setMarca("Marca Valida");
        invalidDTO.setPrecio("1000");
        invalidDTO.setDescripcion(""); // Falta la descripción
        invalidDTO.setImagen("img.png");

        mockMvc.perform(post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    void testAgregarBadRequestImagen() throws Exception {
        ProductoDTO invalidDTO = new ProductoDTO();
        invalidDTO.setNombre("Nombre Valido");
        invalidDTO.setMarca("Marca Valida");
        invalidDTO.setPrecio("1000");
        invalidDTO.setDescripcion("Descripcion valida");
        invalidDTO.setImagen(null); // Falta la imagen

        mockMvc.perform(post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    void testAgregarInternalServerError() throws Exception {
        when(productoService.save(any(Producto.class))).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isInternalServerError());

        verify(productoService, times(1)).save(any(Producto.class));
    }

    @Test
    void testActualizarSuccess() throws Exception {
        ProductoDTO updateDTO = new ProductoDTO();
        updateDTO.setNombre("Producto Actualizado");
        updateDTO.setMarca("Marca Actualizada");
        updateDTO.setPrecio("1500");
        updateDTO.setDescripcion("Desc Actualizada");
        updateDTO.setImagen("updated.png");

        Producto updatedProducto = new Producto();
        updatedProducto.setId(1L);
        updatedProducto.setNombre("Producto Actualizado");
        updatedProducto.setMarca("Marca Actualizada");
        updatedProducto.setPrecio("1500");
        updatedProducto.setDescripcion("Desc Actualizada");
        updatedProducto.setImagen("updated.png");

        when(productoService.save(any(Producto.class))).thenReturn(updatedProducto);

        mockMvc.perform(put("/productos/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto Actualizado"));

        verify(productoService, times(1)).save(any(Producto.class));
    }

    @Test
    void testActualizarBadRequest() throws Exception {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("No Importa");

        mockMvc.perform(put("/productos/actualizar/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    void testActualizarBadRequestNull() throws Exception {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Producto Actualizado");

        mockMvc.perform(put("/productos/actualizar/null")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    void testActualizarInternalServerError() throws Exception {
        when(productoService.save(any(Producto.class))).thenThrow(new RuntimeException("Error interno"));

        ProductoDTO updateDTO = new ProductoDTO();
        updateDTO.setNombre("Producto Actualizado");
        updateDTO.setMarca("Marca Actualizada");
        updateDTO.setPrecio("1500");
        updateDTO.setDescripcion("Desc Actualizada");
        updateDTO.setImagen("updated.png");

        mockMvc.perform(put("/productos/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isInternalServerError());

        verify(productoService, times(1)).save(any(Producto.class));
    }

    @Test
    void testEliminarSuccess() throws Exception {
        mockMvc.perform(delete("/productos/eliminar/1"))
                .andExpect(status().isOk());

        verify(productoService, times(1)).delete(1L);
    }

    @Test
    void testEliminarBadRequest() throws Exception {
        mockMvc.perform(delete("/productos/eliminar/-1"))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).delete(anyLong());
    }

    @Test
    void testEliminarBadRequestNull() throws Exception {
        mockMvc.perform(delete("/productos/eliminar/null"))
                .andExpect(status().isBadRequest());

        verify(productoService, never()).delete(anyLong());
    }

    @Test
    void testEliminarInternalServerError() throws Exception {
        doThrow(new RuntimeException("Error interno")).when(productoService).delete(100L);

        mockMvc.perform(delete("/productos/eliminar/100"))
                .andExpect(status().isInternalServerError());

        verify(productoService, times(1)).delete(100L);
    }
}

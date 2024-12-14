package cl.duoc.app.controller;

import cl.duoc.app.dto.ProductoDTO;
import cl.duoc.app.mapper.ProductoMapper;
import cl.duoc.app.model.Producto;
import cl.duoc.app.service.ProductoService;
import cl.duoc.app.util.UtilProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoDTO>> listar() {
        try {
            List<ProductoDTO> productosDTO = productoService.findAll()
                    .stream()
                    .map(ProductoMapper::toDTO)
                    .toList();
            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoDTO> buscar(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Optional<Producto> producto = productoService.findById(id);
            return producto.map(value -> ResponseEntity.ok(ProductoMapper.toDTO(value)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ProductoDTO> agregar(@RequestBody ProductoDTO productoDTO) { //se utiliza patron dto
        try {
            if (UtilProducto.isEmptyOrNull(productoDTO.getNombre())
                    || UtilProducto.isEmptyOrNull(productoDTO.getMarca())
                    || UtilProducto.isEmptyOrNull(productoDTO.getPrecio())
                    || UtilProducto.isEmptyOrNull(productoDTO.getDescripcion())
                    || UtilProducto.isEmptyOrNull(productoDTO.getImagen())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Producto producto = ProductoMapper.toEntity(productoDTO); //utilizamos el mapper para aplicar el patron dto
            Producto savedProducto = productoService.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ProductoMapper.toDTO(savedProducto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Producto producto = ProductoMapper.toEntity(productoDTO);
            producto.setId(id);
            Producto updatedProducto = productoService.save(producto);
            return ResponseEntity.ok(ProductoMapper.toDTO(updatedProducto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            productoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

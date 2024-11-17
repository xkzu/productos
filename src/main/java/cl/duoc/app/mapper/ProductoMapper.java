package cl.duoc.app.mapper;

import cl.duoc.app.dto.ProductoDTO;
import cl.duoc.app.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    private ProductoMapper() {}

    public static ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setMarca(producto.getMarca());
        dto.setStock(producto.getStock());
        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setMarca(dto.getMarca());
        producto.setStock(dto.getStock());
        return producto;
    }
}


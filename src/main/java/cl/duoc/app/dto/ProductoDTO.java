package cl.duoc.app.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String precio;
    private String marca;
    private int stock;
}

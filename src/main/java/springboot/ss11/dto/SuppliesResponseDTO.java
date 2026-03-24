package springboot.ss11.dto;

import lombok.Data;

@Data
public class SuppliesResponseDTO {

    private Long id;
    private String name;
    private String specification;
    private String provider;
    private String unit;
    private Integer quantity;
}

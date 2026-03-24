package springboot.ss11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopSuppliesExportResponse {
    private String topSupplyName;
    private Long totalExportQuantity;
}
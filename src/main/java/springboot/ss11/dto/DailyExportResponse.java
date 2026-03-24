package springboot.ss11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyExportResponse {
    private Long supplyId;
    private String supplyName;
    private Long totalQuantity;
}
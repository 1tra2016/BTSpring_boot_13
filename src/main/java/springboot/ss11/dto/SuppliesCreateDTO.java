package springboot.ss11.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SuppliesCreateDTO {
    @NotBlank
    private String name;

    @Size(max = 254, message = "specification không được quá 254 kí tự")
    private String specification;

    @Size(max = 254, message = "tên nhà  sản xuất không được quá 254 kí tự")
    private String provider;

    @NotBlank(message = "Đơn vị tính không được trống")
    private String unit;
}

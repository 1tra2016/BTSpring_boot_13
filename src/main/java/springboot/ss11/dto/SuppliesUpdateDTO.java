package springboot.ss11.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = false)
public class SuppliesUpdateDTO {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Size(max = 254, message = "Đặc tả vật tư không được quá 254 kí tự")
    private String specification;

    @Size(max = 254, message = "Tên nhà sản xuất không được quá 254 kí tự")
    private String provider;
}

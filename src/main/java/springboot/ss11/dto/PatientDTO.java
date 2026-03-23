package springboot.ss11.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    @NotBlank(message = "Name không được trống")
    private String name;

    @NotNull(message = "Age không được trống")
    @Min(value = 0, message = "Age phải lốnư hơn 0")
    private Integer age;
}
package springboot.ss11.mapper;
import org.springframework.stereotype.Component;
import springboot.ss11.dto.PatientDTO;
import springboot.ss11.entity.Patient;

@Component
public class PatientMapper {

    public Patient toEntity(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        return patient;
    }

    public PatientDTO toDTO(Patient entity) {
        PatientDTO dto = new PatientDTO();
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        return dto;
    }
}
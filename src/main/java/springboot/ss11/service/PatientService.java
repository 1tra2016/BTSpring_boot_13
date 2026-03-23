package springboot.ss11.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.ss11.dto.PatientDTO;
import springboot.ss11.entity.Patient;
import springboot.ss11.mapper.PatientMapper;
import springboot.ss11.repository.PatientRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepo;
    private final PatientMapper patientMapper;

    public Patient savePatient(PatientDTO req) {
        Patient patient = patientRepo.save(patientMapper.toEntity(req));

        if (req.getAge() > 120) log.warn("Người dùng nhập tuổi quá lớn: {}", req.getAge());
        log.info("Lưu thông tin bệnh nhân mới: {}", patient.getName());

        return patient;
    }
}

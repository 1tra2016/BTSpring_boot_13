package springboot.ss11.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.ss11.dto.ApiResponse;
import springboot.ss11.dto.PatientDTO;
import springboot.ss11.entity.Patient;
import springboot.ss11.service.PatientService;

@RestController
@RequestMapping("/api/v1/patients")
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ApiResponse<Patient>> addPatient(
                @Valid @RequestBody PatientDTO req
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                patientService.savePatient(req)
        ));
    }
}
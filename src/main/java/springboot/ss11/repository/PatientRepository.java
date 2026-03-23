package springboot.ss11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ss11.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}

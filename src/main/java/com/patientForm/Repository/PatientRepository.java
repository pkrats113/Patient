package com.patientForm.Repository;

import com.patientForm.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {

}

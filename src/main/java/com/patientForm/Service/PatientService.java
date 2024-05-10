package com.patientForm.Service;

import com.patientForm.payload.PatientDto;

import java.util.List;

public interface PatientService {
    PatientDto registerPatient(PatientDto patientDto);

    PatientDto updatePatient(long id, PatientDto patientDto);

    String deleteRecord(long id);

    List<PatientDto> getAllPatient(int pageNo, int pageSize, String sortBy, String sortDir);
}

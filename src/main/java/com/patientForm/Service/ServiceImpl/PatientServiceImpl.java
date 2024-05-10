package com.patientForm.Service.ServiceImpl;

import com.patientForm.Entity.Patient;
import com.patientForm.Exception.ResourceNotFoundException;
import com.patientForm.Repository.PatientRepository;
import com.patientForm.Service.PatientService;
import com.patientForm.payload.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientDto registerPatient(PatientDto patientDto) {
        Patient patient = mapToEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);

        PatientDto dto = mapToDto(savedPatient);
        dto.setMessage("new user generated");
        return dto;
    }

    @Override
    public PatientDto updatePatient(long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("record not found with id: " + id)
        );
        patient.setPatientName(patientDto.getPatientName());
        patient.setAge(patientDto.getAge());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setDisease(patientDto.getDisease());
        Patient saved = patientRepository.save(patient);
        PatientDto dto = mapToDto(saved);
        dto.setMessage("patient record is updated");
        return dto;


    }

    @Override
    public String deleteRecord(long id) {
       patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("record not found with id: " + id)
        );
        patientRepository.deleteById(id);
        return "record deleted with id: "+id;
    }

    @Override
    public List<PatientDto> getAllPatient(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo,pageSize, sort);
        Page<Patient> all = patientRepository.findAll(pageable);
        List<Patient> content = all.getContent();
        List<PatientDto> collect = content.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
        return collect;
    }


    public Patient mapToEntity(PatientDto patientDto){
        Patient patient=new Patient();
        patient.setId(patientDto.getId());
        patient.setPatientName(patientDto.getPatientName());
        patient.setAge(patientDto.getAge());
        patient.setGender(patientDto.getGender());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setDisease(patientDto.getDisease());
        return patient;
    }

    public PatientDto mapToDto(Patient patient){

        PatientDto dto=new PatientDto();
        dto.setId(patient.getId());
        dto.setPatientName(patient.getPatientName());
        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setEmail(patient.getEmail());
        dto.setPhoneNumber(patient.getPhoneNumber());
        dto.setDisease(patient.getDisease());
        return dto;
    }
}

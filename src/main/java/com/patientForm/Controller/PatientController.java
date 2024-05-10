package com.patientForm.Controller;

import com.patientForm.Service.PatientService;
import com.patientForm.payload.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

   @PreAuthorize("hasRole('ADMIN')")
   @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@Validated @RequestBody PatientDto patientDto, BindingResult bindingResult){
       if(bindingResult.hasErrors()){
           return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }else {

           PatientDto patientDto1 = patientService.registerPatient(patientDto);
           return new ResponseEntity<>(patientDto1, HttpStatus.CREATED);
       }
    }
  //http://localhost:8080/api/patient/update/1

    @PutMapping("update/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable long id,@RequestBody PatientDto patientDto){
       PatientDto pd=patientService.updatePatient(id,patientDto);
       return new ResponseEntity<>(pd,HttpStatus.OK);
    }
    //http://localhost:8080/api/patient/delete?id=
    @DeleteMapping("/delete")
    public  ResponseEntity<String> deleteRecord(@RequestParam long id){
       String message=patientService.deleteRecord(id);
       return new ResponseEntity<>(message,HttpStatus.OK);
    }
//http://localhost:8080/api/patient/get?pageNo=3&pageSize=5


   @GetMapping("/get")
public ResponseEntity<List<PatientDto>> getAllPatient(
        @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
        @RequestParam(name="pageSize",defaultValue = "3",required = false) int pageSize,
        @RequestParam(name="sortBy",defaultValue = "patientName",required = false) String sortBy,

        @RequestParam(name="sortDir",defaultValue = "ASC",required = false) String sortDir
   ){
       List<PatientDto> dto=patientService.getAllPatient(pageNo,pageSize,sortBy,sortDir);
       return new ResponseEntity<>(dto,HttpStatus.OK);
   }


}

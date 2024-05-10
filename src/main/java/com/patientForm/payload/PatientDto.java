package com.patientForm.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private long id;
    @NotEmpty
    @Size(min=2,message ="name cannot be less than 2 character")
    private String patientName;
    private String age;
    @NotEmpty
    @Size(min=2,message ="gender cannot be less than 2 character")
    private String gender;
    @NotEmpty
    @Email
    private String email;
    private long phoneNumber;
    private String disease;
    private String message;
}

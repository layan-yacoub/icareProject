package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffDay {
    Long nutritionist_id ;
    LocalDate offDay;
}

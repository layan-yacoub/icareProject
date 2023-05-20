package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetStartEndTime {
    Long nutritionist_id;
    LocalTime start;
    LocalTime end;
}

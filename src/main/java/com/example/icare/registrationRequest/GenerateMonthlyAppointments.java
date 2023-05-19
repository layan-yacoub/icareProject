package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateMonthlyAppointments {

    int year;
    Month month;
    LocalTime startTime;
    LocalTime endTime;
    List<DayOfWeek> excludedDays ;
}

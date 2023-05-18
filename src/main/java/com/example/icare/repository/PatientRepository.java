package com.example.icare.repository;

import com.example.icare.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface PatientRepository extends JpaRepository<Patient, Long> {
   // void findByEmail(String email);
     Patient findByEmail(String email);
}

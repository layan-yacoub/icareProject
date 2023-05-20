package com.example.icare.repository;

import com.example.icare.domain.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Transactional
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

}

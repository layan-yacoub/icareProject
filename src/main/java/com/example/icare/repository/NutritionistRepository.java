package com.example.icare.repository;


import com.example.icare.domain.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository//JPA Repository is mainly used for managing the data in a Spring Boot Application
@Transactional(readOnly = true)
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {
    List<Nutritionist> findByStatus(boolean status);

    Nutritionist findByEmail(String email);

    @Query("SELECT n FROM Nutritionist n WHERE n.nutritionist_id = :id")
    Nutritionist getNutritionistById(Long id);



}

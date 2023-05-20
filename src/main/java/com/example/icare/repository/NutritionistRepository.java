package com.example.icare.repository;


import com.example.icare.domain.Nutritionist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository//JPA Repository is mainly used for managing the data in a Spring Boot Application
@Transactional(readOnly = true)
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {
    List<Nutritionist> findByStatus(boolean status);

    Nutritionist findByEmail(String email);

    @Query("SELECT n FROM Nutritionist n WHERE n.nutritionist_id = :id")
    Nutritionist getNutritionistById(Long id);

    @Query("SELECT n FROM Nutritionist n LEFT JOIN FETCH n.offDays WHERE n.id = :nutritionistId")
    Nutritionist fetchOffDays(@Param("nutritionistId") Long nutritionistId);


}

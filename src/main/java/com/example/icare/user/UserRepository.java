package com.example.icare.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository //JPA Repository is mainly used for managing the data in a Spring Boot Application
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Long> {


}

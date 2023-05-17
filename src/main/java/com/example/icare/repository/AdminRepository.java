package com.example.icare.repository;
import com.example.icare.domain.Admin;
import com.example.icare.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

}



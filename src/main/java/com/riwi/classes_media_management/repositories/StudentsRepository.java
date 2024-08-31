package com.riwi.classes_media_management.repositories;

import com.riwi.classes_media_management.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Method to find a student by e-mail
    Optional<Student> findByEmail(String email);

    // Method to find all active students
    List<Student> findByActiveTrue();
}

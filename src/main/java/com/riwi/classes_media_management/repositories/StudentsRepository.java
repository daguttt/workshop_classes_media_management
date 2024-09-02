package com.riwi.classes_media_management.repositories;

import com.riwi.classes_media_management.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Long> {

    // Method to find a student by e-mail
    Optional<Student> findByEmail(String email);

    //Method to find a student by name
    Optional<Student> findByName(String name);

    // Method to find all active students
    List<Student> findByActiveTrue();

    boolean existsByEmail(String email);

    Page<Student> findByActiveTrue(Pageable pageable);

}

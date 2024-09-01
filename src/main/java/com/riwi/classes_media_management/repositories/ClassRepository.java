package com.riwi.classes_media_management.repositories;

import com.riwi.classes_media_management.entities.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

    List<ClassEntity> findByActiveTrue();

    Optional<ClassEntity> findById(Long id);
}

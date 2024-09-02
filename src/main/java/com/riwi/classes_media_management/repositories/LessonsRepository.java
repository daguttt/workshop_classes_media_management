package com.riwi.classes_media_management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.classes_media_management.entities.Lesson;

public interface LessonsRepository extends JpaRepository<Lesson, Long> {
  Optional<Lesson> findByIdAndActiveTrue(Long ID);
}

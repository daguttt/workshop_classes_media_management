package com.riwi.classes_media_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.dtos.LessonDisabledDTO;
import com.riwi.classes_media_management.dtos.LessonReponseDTO;
import com.riwi.classes_media_management.services.interfaces.ILessonsService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonsController {

  @Autowired
  private ILessonsService lessonsService;

  @PostMapping
  public ResponseEntity<LessonReponseDTO> create(@Valid @ModelAttribute LessonDTO lessonDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.lessonsService.create(lessonDTO));
  }

  @PatchMapping("/{id}/disable")
  public ResponseEntity<LessonDisabledDTO> disable(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.lessonsService.disable(id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LessonReponseDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(this.lessonsService.getById(id));
  }

}

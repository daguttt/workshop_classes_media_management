package com.riwi.classes_media_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.entities.Lesson;
import com.riwi.classes_media_management.services.interfaces.ILessonsService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

  @Autowired
  private ILessonsService lessonsService;

  @PostMapping
  public ResponseEntity<Lesson> create(@Valid @ModelAttribute LessonDTO lessonDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.lessonsService.create(lessonDTO));
  }

}

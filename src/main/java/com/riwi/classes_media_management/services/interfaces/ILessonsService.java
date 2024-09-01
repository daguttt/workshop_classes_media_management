package com.riwi.classes_media_management.services.interfaces;

import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.entities.Lesson;

public interface ILessonsService {
  public Lesson create(LessonDTO lessonDTO);
}

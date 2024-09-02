package com.riwi.classes_media_management.services.interfaces;

import com.riwi.classes_media_management.dtos.LessonReponseDTO;

import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.dtos.LessonDisabledDTO;

public interface ILessonsService {
  public LessonReponseDTO create(LessonDTO lessonDTO);

  public LessonReponseDTO getById(Long id);

  public LessonDisabledDTO disable(Long id);
}

package com.riwi.classes_media_management.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.entities.Lesson;
import com.riwi.classes_media_management.enums.MediaTypes;
import com.riwi.classes_media_management.services.interfaces.ILessonsService;
import com.riwi.classes_media_management.storage.IStorageService;

@Service
public class LessonsService implements ILessonsService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private IStorageService storageService;

  @Override
  public Lesson create(LessonDTO lessonDTO) {
    String fileUrl = null;
    boolean uplodingAudioOrVideo = !lessonDTO.getMediaType().equals(MediaTypes.DOCUMENT);
    // Store file in case of audios or videos
    if (uplodingAudioOrVideo) {
      try {
        fileUrl = storageService.store(lessonDTO.getFile());
      } catch (IOException e) {
        this.logger.error("Error storing lesson file", e);
      }
    }
    // Create the entity
    Lesson lesson = Lesson.builder()
        .name(lessonDTO.getName())
        .mediaType(lessonDTO.getMediaType())
        .url(fileUrl)
        .build();

    // TODO: Save the entity in the DB

    return lesson;
  }
}

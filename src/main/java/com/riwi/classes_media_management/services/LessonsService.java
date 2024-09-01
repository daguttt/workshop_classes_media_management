package com.riwi.classes_media_management.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.entities.Lesson;
import com.riwi.classes_media_management.enums.MediaTypes;
import com.riwi.classes_media_management.services.interfaces.ILessonsService;
import com.riwi.classes_media_management.storage.FileFormatValidator;
import com.riwi.classes_media_management.storage.IStorageService;
import com.riwi.classes_media_management.storage.exceptions.StorageException;

@Service
public class LessonsService implements ILessonsService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private IStorageService storageService;

  @Autowired
  private FileFormatValidator fileFormatValidator;

  @Override
  public Lesson create(LessonDTO lessonDTO) {
    // Validate mediaType as document
    var isMediaTypeDocument = lessonDTO.getMediaType().equals(MediaTypes.DOCUMENT);
    var hasContentProperty = !lessonDTO.getContent().isEmpty();
    var isFileEmpty = lessonDTO.getFile().isEmpty();

    if (!isFileEmpty && isMediaTypeDocument && hasContentProperty)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uploading file when 'mediaType' is 'DOCUMENT'");

    // Validate mediaType as Video or Audio
    var isVideoOrAudioFormatValid = fileFormatValidator.validate(lessonDTO.getFile(), lessonDTO.getMediaType());
    boolean isFileValid = (isMediaTypeDocument && hasContentProperty)
        || isVideoOrAudioFormatValid;

    if (!isFileValid)
      throw new StorageException("Invalid file format for the selected media type");

    String fileUrl = null;
    boolean uplodingAudioOrVideo = !isMediaTypeDocument;
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

package com.riwi.classes_media_management.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.riwi.classes_media_management.dtos.LessonReponseDTO;
import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.dtos.LessonDisabledDTO;
import com.riwi.classes_media_management.entities.Lesson;
import com.riwi.classes_media_management.repositories.ClassRepository;
import com.riwi.classes_media_management.repositories.LessonsRepository;
import com.riwi.classes_media_management.services.interfaces.ILessonsService;
import com.riwi.classes_media_management.storage.FileFormatValidator;
import com.riwi.classes_media_management.storage.IStorageService;

@Service
public class LessonsService implements ILessonsService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private IStorageService storageService;

  @Autowired
  private FileFormatValidator fileFormatValidator;

  @Autowired
  private ClassRepository classRepository;

  @Autowired
  private LessonsRepository lessonsRepository;

  @Override
  public LessonReponseDTO create(LessonDTO lessonDTO) {
    // Get related classEntity
    var classEntityOptional = classRepository.findById(lessonDTO.getClassId());
    if (classEntityOptional.isEmpty())
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          String.format("Related class with id: '%d' not found",
              lessonDTO.getClassId()));

    String fileUrl = null;

    // Business validations based on mediaType
    switch (lessonDTO.getMediaType()) {
      case DOCUMENT -> {
        var hasFileProperty = lessonDTO.getFile() != null && !lessonDTO.getFile().isEmpty();
        if (hasFileProperty)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uploading file when 'mediaType' is 'DOCUMENT'");

        // Check content field
        var hasContentProperty = !lessonDTO.getContent().isEmpty();
        if (!hasContentProperty)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "No content provided using 'DOCUMENT' as mediaType");
      }
      case AUDIO, VIDEO -> {
        // Check content field
        var hasContentProperty = !lessonDTO.getContent().isEmpty();
        if (!hasContentProperty)
          throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              String.format(
                  "Content provided using '%s' as mediaType",
                  lessonDTO.getMediaType()));

        var isFileValid = lessonDTO.getFile() != null && !lessonDTO.getFile().isEmpty();
        if (!isFileValid)
          throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              "File is null or empty");

        // Validate file format
        boolean isFileFormatValid = fileFormatValidator.validate(lessonDTO.getFile(), lessonDTO.getMediaType());
        if (!isFileFormatValid)
          throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              "Invalid file format for the selected media type");

        try {
          fileUrl = storageService.store(lessonDTO.getFile());
        } catch (IOException e) {
          this.logger.error("Error storing lesson file", e);
        }
      }
    }

    // Create the entity
    Lesson lesson = Lesson.builder()
        .name(lessonDTO.getName())
        .mediaType(lessonDTO.getMediaType())
        .content(lessonDTO.getContent())
        .classEntity(classEntityOptional.get())
        .url(fileUrl)
        .build();

    this.lessonsRepository.save(lesson);

    return LessonReponseDTO.builder()
        .id(lesson.getId())
        .name(lesson.getName())
        .mediaType(lesson.getMediaType())
        .content(lesson.getContent())
        .url(lesson.getUrl())
        .classId(lesson.getClassEntity().getId())
        .build();
  }

  @Override
  public LessonReponseDTO getById(Long id) {
    var foundLessonOptional = this.lessonsRepository.findByIdAndActiveTrue(id);

    if (foundLessonOptional.isEmpty())
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          String.format("Lesson with id '%d' not found", id));

    return LessonReponseDTO.builder()
        .id(foundLessonOptional.get().getId())
        .name(foundLessonOptional.get().getName())
        .mediaType(foundLessonOptional.get().getMediaType())
        .content(foundLessonOptional.get().getContent())
        .url(foundLessonOptional.get().getUrl())
        .classId(foundLessonOptional.get().getClassEntity().getId())
        .build();
  }

  @Override
  public LessonDisabledDTO disable(Long id) {
    var lessonToDisableOptional = this.lessonsRepository.findById(id);

    if (lessonToDisableOptional.isEmpty())
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          String.format("Lesson with id '%d' not found", id));

    var lesson = lessonToDisableOptional.get();
    lesson.setActive(false);
    this.lessonsRepository.save(lesson);

    return LessonDisabledDTO.builder()
        .status(HttpStatus.NO_CONTENT.value())
        .message("asdfasdf")
        .build();

  }

}

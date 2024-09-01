package com.riwi.classes_media_management.dtos;

import com.riwi.classes_media_management.enums.MediaTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonReponseDTO {
  private Long id;
  private String name;
  private MediaTypes mediaType;
  private String content;
  private String url;
  private Long classId;
}
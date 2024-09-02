package com.riwi.classes_media_management.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {

  private Long id;
  private String name;
  private String email;
  private Boolean active;

}
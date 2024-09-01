package com.riwi.classes_media_management.dtos;

import com.riwi.classes_media_management.enums.MediaTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Media Type cannot be null")
    private MediaTypes mediaType;

    @Builder.Default
    private String content = "";

    private MultipartFile file;

    @NotNull(message = "Class ID cannot be null")
    private Long classId;

}

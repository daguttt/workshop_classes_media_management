package com.riwi.classes_media_management.dtos;

import com.riwi.classes_media_management.enums.MediaTypes;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class LessonDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Media Type cannot be null")
    private MediaTypes mediaType;

    private String content;

    @URL(message = "Url needs to be valid")
    private String url;

    @NotNull(message = "Class ID cannot be null")
    private Long classId;

}
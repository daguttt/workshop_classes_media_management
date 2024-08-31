package com.riwi.classes_media_management.dtos;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
public class ClassDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

}

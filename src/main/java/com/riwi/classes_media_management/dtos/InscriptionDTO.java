package com.riwi.classes_media_management.dtos;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
public class InscriptionDTO {

    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Class ID cannot be null")
    private Long classId;
}

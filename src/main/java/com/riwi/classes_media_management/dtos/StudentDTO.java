package com.riwi.classes_media_management.dtos;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
public class StudentDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;


    @NotBlank(message = "Password cannot be blank")
    private String password;

}

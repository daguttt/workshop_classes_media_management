package com.riwi.classes_media_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.classes_media_management.dtos.LessonDTO;
import com.riwi.classes_media_management.dtos.LessonDisabledDTO;
import com.riwi.classes_media_management.dtos.LessonReponseDTO;
import com.riwi.classes_media_management.services.interfaces.ILessonsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonsController {

  @Autowired
  private ILessonsService lessonsService;

  @Operation(summary = "Create a class' lesson")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Lesson successfully created", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = LessonReponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Invalid file format", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "404", description = "`classId` not found", content = {
          @Content(mediaType = "application/json")
      }),
  })
  @RequestBody(description = "Schema to create a Lesson.\n"
      + "- When *'DOCUMENT'* `mediaType` is selected, the `content` field is **required** and `file` field should be empty.\n"
      + "- When *'AUDIO'* or *'VIDEO'* `mediaType` is selected, the `file` field is **required** and the `content` field should be empty.", content = {
          @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = LessonDTO.class))
      })
  @PostMapping
  public ResponseEntity<LessonReponseDTO> create(@Valid @ModelAttribute LessonDTO lessonDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.lessonsService.create(lessonDTO));
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lesson successfully disabled", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = LessonDisabledDTO.class))
      }),
      @ApiResponse(responseCode = "404", description = "Lesson's `id` not found", content = {
          @Content(mediaType = "application/json")
      }),
  })
  @PatchMapping("/{id}/disable")
  public ResponseEntity<LessonDisabledDTO> disable(@PathVariable Long id) {
    return ResponseEntity.ok(this.lessonsService.disable(id));
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lesson successfully fetched", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = LessonReponseDTO.class))
      }),
      @ApiResponse(responseCode = "404", description = "Lesson's `id` not found", content = {
          @Content(mediaType = "application/json")
      }),
  })
  @GetMapping("/{id}")
  public ResponseEntity<LessonReponseDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(this.lessonsService.getById(id));
  }

}

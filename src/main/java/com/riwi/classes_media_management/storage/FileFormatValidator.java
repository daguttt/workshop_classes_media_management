package com.riwi.classes_media_management.storage;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.riwi.classes_media_management.enums.AudioFormats;
import com.riwi.classes_media_management.enums.MediaTypes;
import com.riwi.classes_media_management.enums.VideoFormats;

@Component
public class FileFormatValidator {
  public boolean validate(MultipartFile file, MediaTypes mediaType) {
    return switch (mediaType) {
      case VIDEO -> Arrays.stream(VideoFormats.values())
          .map(VideoFormats::getFormat)
          .anyMatch(videoFormat -> videoFormat.equals(file.getContentType()));
      case AUDIO -> Arrays.stream(AudioFormats.values())
          .map(AudioFormats::getFormat)
          .anyMatch(audioFormat -> audioFormat.equals(file.getContentType()));
      default -> false;
    };
  }
}

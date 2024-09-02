package com.riwi.classes_media_management.enums;

import lombok.Getter;

@Getter
public enum VideoFormats {
  MP4("video/mp4"),
  WEBM("video/webm");

  private final String format;

  VideoFormats(String format) {
    this.format = format;
  }
}

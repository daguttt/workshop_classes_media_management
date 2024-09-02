package com.riwi.classes_media_management.enums;

import lombok.Getter;

@Getter
public enum AudioFormats {
  WEBM("audio/webm"),
  MP3("audio/mpeg");

  private final String format;

  AudioFormats(String format) {
    this.format = format;
  }

}

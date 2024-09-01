package com.riwi.classes_media_management.storage;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  String store(MultipartFile file) throws IOException;
}

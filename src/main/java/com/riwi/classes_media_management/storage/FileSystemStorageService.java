package com.riwi.classes_media_management.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.riwi.classes_media_management.storage.exceptions.StorageException;

@Service
public class FileSystemStorageService implements IStorageService {
  private final Path rootLocation;

  public FileSystemStorageService(StorageProperties properties) {
    if (properties.getLocation().trim().length() == 0)
      throw new StorageException("File upload location cannot be empty");

    this.rootLocation = Paths.get(properties.getLocation());

  }

  @Override
  public String store(MultipartFile file) {
    try {
      if (file.isEmpty())
        throw new StorageException("Failed to store empty file.");

      Path destinationFile = this.rootLocation
          .resolve(
              Paths.get(file.getOriginalFilename()))
          .normalize()
          .toAbsolutePath();

      // This is a security check
      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath()))
        throw new StorageException(
            "Cannot store file outside current directory.");

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(
            inputStream,
            destinationFile,
            StandardCopyOption.REPLACE_EXISTING);
        return "/static/" + file.getOriginalFilename();
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }
  }

}

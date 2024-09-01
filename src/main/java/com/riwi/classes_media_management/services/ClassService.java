package com.riwi.classes_media_management.services;

import com.riwi.classes_media_management.dtos.ClassDTO;
import com.riwi.classes_media_management.entities.ClassEntity;
import com.riwi.classes_media_management.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<ClassDTO> getAllActiveClasses() {
        List<ClassEntity> activeClasses = classRepository.findByActiveTrue();
        return activeClasses.stream()
                .map(classEntity -> ClassDTO.builder()
                        .name(classEntity.getName())
                        .description(classEntity.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public ClassDTO getClassById(Long id) {
        Optional<ClassEntity> classEntityOptional = classRepository.findById(id);
        return classEntityOptional.map(classEntity -> ClassDTO.builder()
                        .name(classEntity.getName())
                        .description(classEntity.getDescription())
                        .build())
                .orElse(null);
    }
}

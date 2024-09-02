package com.riwi.classes_media_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.riwi.classes_media_management.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ClassesMediaManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassesMediaManagementApplication.class, args);
	}

}

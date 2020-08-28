package br.com.rodrigodoe.springbootapi.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import br.com.rodrigodoe.springbootapi.config.FileStorageConfig;
import br.com.rodrigodoe.springbootapi.exception.FileStorageException;
import br.com.rodrigodoe.springbootapi.exception.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("could not create the directory where the uplaod files will be stored", e);
		}
	}

	public String storeFile(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (filename.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalide path sequence " + filename);
			}

			Path targetLocation = this.fileStorageLocation.resolve(filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return filename;
		} catch (Exception e) {
			throw new FileStorageException("could not store file " + filename + ", Please try again! ", e);

		}
	}

	public Resource loadFileAsResource(String fileName) {

		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found" + fileName);

			}

		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found" + fileName, e);

		}

	}

}

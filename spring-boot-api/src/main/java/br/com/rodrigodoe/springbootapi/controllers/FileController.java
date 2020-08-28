package br.com.rodrigodoe.springbootapi.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.net.HttpHeaders;

import br.com.rodrigodoe.springbootapi.data.vo.UploadFileResponseVO;
import br.com.rodrigodoe.springbootapi.services.FileStorageService;
import io.swagger.annotations.Api;

@Api(tags = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public UploadFileResponseVO uplaodFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
		String fileDownladUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/v1/downloadFile/")
				.path(fileName).toUriString();
		return new UploadFileResponseVO(fileName, fileDownladUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uplaodFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

		Resource resource = fileStorageService.loadFileAsResource(fileName);

		String contentType = null;

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.info("Could not determine file type");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

}

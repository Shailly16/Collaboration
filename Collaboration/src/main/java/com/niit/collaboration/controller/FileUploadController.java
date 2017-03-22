package com.niit.collaboration.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.niit.collaboration.util.FileUtil;



@RestController
// Max uploaded file size (here it is 20 MB)
@MultipartConfig(fileSizeThreshold = 20971520)
public class FileUploadController {
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

	// @CrossOrigin(origins = "http://localhost:8083")
	@PostMapping(value = "/upload")
	public void uploadFile(@RequestParam("fileName") String name,@RequestParam("uploadedFile") MultipartFile multipartFile) {
		log.debug("Calling the method uploadFile");
		FileUtil.upload(multipartFile, name);
		log.debug("Ending the method uploadFile");
		
	}

}

package cn.wisdom.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	/**
	 * Save upload file.
	 * 
	 * @param multipartFile
	 * @param fileName
	 * @return
	 */
	String saveUploadFile(MultipartFile multipartFile, String fileName);
}

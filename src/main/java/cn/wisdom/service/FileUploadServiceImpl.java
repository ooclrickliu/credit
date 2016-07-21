package cn.wisdom.service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.dao.vo.AppProperty;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	@Autowired
	private AppProperty appProperty;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class.getName());
    
	@Override
	public String saveUploadFile(MultipartFile multipartFile, String fileName) {
		String dir = appProperty.fileUploadDir;
		
		ThreadLocalRandom random = ThreadLocalRandom.current();
		fileName = dir + fileName + random.nextInt(100);

		File file = new File(dir + fileName);

		try {
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
		} catch (IOException e) {
			logger.error(
					"Failed to save upload file - " + multipartFile.getName(),
					e);
		}

		return fileName;
	}

}

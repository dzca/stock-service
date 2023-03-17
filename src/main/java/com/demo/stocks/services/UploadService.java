package com.demo.stocks.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    void saveFile(String accountName, String fileName, MultipartFile multipartFile)
            throws IOException;
}

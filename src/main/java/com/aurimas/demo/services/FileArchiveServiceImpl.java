package com.aurimas.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
public class FileArchiveServiceImpl implements FileArchiveService {

    @Override
    public StreamingResponseBody archiveFiles(MultipartFile[] files, String type) {
        return null;
    }
}

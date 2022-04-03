package com.aurimas.demo.services;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface FileArchiveService {
    StreamingResponseBody archiveFiles(MultipartFile[] files, String type);
}

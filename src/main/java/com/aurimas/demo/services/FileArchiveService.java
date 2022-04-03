package com.aurimas.demo.services;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface FileArchiveService {
    StreamingResponseBody archiveFiles(MultipartFile[] files, String archiveMethod, OutputStream outputStream);

    String getExtension(String method);
}

package com.aurimas.demo.services;

import java.io.OutputStream;

import com.aurimas.demo.util.ArchiveMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface FileArchiveService {
    StreamingResponseBody archiveFiles(MultipartFile[] files, ArchiveMethod archiveMethod, OutputStream outputStream);
}

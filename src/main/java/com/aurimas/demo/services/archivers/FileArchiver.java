package com.aurimas.demo.services.archivers;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public abstract class FileArchiver {
    public abstract boolean canArchive(String method);

    public abstract String getExtension(String method);

    public abstract StreamingResponseBody archive(MultipartFile[] files, OutputStream outputStream);
}

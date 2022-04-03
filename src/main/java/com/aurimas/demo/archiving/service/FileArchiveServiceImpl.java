package com.aurimas.demo.archiving.service;

import java.io.OutputStream;
import java.util.List;

import com.aurimas.demo.archiving.service.archivers.FileArchiver;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
public class FileArchiveServiceImpl implements FileArchiveService {
    private final List<FileArchiver> archivers;

    public FileArchiveServiceImpl(List<FileArchiver> archivers) {
        this.archivers = archivers;
    }

    @Override
    public StreamingResponseBody archiveFiles(MultipartFile[] files, String archiveMethod, OutputStream outputStream) {
        final FileArchiver archiver = archivers.stream()
                .filter(a -> a.canArchive(archiveMethod))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported archive method: " + archiveMethod));

        return archiver.archive(files, outputStream);
    }

    @Override
    public String getExtension(String method) {
        final FileArchiver fileArchiver = archivers.stream()
                .filter(a -> a.canArchive(method))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported archive method: " + method));

        return fileArchiver.getExtension(method);
    }
}

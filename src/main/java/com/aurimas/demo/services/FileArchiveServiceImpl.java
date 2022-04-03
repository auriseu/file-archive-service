package com.aurimas.demo.services;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.aurimas.demo.util.ArchiveMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
public class FileArchiveServiceImpl implements FileArchiveService {

    @Override
    public StreamingResponseBody archiveFiles(MultipartFile[] files, ArchiveMethod archiveMethod, OutputStream outputStream) {

        return out -> {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {

                for (MultipartFile file : files) {
                    ZipEntry zipEntry = new ZipEntry(StringUtils.defaultIfBlank(file.getOriginalFilename(), "file_" + LocalDateTime.now()));
                    zipOutputStream.putNextEntry(zipEntry);

                    StreamUtils.copy(file.getInputStream(), zipOutputStream);
                    zipOutputStream.closeEntry();
                }

                zipOutputStream.finish();
            }
        };
    }
}

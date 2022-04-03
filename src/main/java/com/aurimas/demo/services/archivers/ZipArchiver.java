package com.aurimas.demo.services.archivers;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
public class ZipArchiver extends FileArchiver {
    public static final String METHOD_ZIP = "zip";

    @Override
    public boolean canArchive(String method) {
        return METHOD_ZIP.equals(method);
    }

    @Override
    public String getExtension(String method) {
        return METHOD_ZIP;
    }

    @Override
    public StreamingResponseBody archive(MultipartFile[] files, OutputStream outputStream) {
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

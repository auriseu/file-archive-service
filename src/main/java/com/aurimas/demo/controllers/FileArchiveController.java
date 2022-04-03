package com.aurimas.demo.controllers;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.aurimas.demo.util.ArchiveMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/files")
public class FileArchiveController {

    @PostMapping(value = {"/archive", "/archive/{type}"})
    public ResponseEntity<StreamingResponseBody> archiveFiles(HttpServletResponse response,
                                                              @RequestParam("files") MultipartFile[] files,
                                                              @PathVariable("type") Optional<String> type) {

        final ArchiveMethod archiveMethod = ArchiveMethod.fromExtension(type.orElse("zip"));
        StreamingResponseBody streamResponseBody = out -> {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {

                for (MultipartFile file : files) {
                    ZipEntry zipEntry = new ZipEntry(StringUtils.defaultIfBlank(file.getOriginalFilename(), "file_" + LocalDateTime.now()));
                    zipOutputStream.putNextEntry(zipEntry);

                    StreamUtils.copy(file.getInputStream(), zipOutputStream);
                    zipOutputStream.closeEntry();
                }

                zipOutputStream.finish();
            }
        };

        String name = "archive" + "." + archiveMethod.getExtension();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "filename=" + name);

        return ResponseEntity.ok(streamResponseBody);
    }
}

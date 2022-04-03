package com.aurimas.demo.controllers;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipOutputStream;

import com.aurimas.demo.services.FileArchiveService;
import com.aurimas.demo.util.ArchiveMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final FileArchiveService fileArchiveService;

    public FileArchiveController(FileArchiveService fileArchiveService) {
        this.fileArchiveService = fileArchiveService;
    }

    @PostMapping(value = {"/archive", "/archive/{type}"})
    public ResponseEntity<StreamingResponseBody> archiveFiles(HttpServletResponse response,
                                                              @RequestParam("files") MultipartFile[] files,
                                                              @PathVariable("type") Optional<String> type) throws IOException {

        final ArchiveMethod archiveMethod = ArchiveMethod.fromExtension(type.orElse("zip"));
        final StreamingResponseBody responseBody = fileArchiveService.archiveFiles(files, archiveMethod, response.getOutputStream());

        String name = "archive" + "." + archiveMethod.getExtension();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "filename=" + name);

        return ResponseEntity.ok(responseBody);
    }
}

package com.aurimas.demo.archiving.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import com.aurimas.demo.archiving.service.FileArchiveService;
import com.aurimas.demo.archiving.archivers.ZipArchiver;
import com.aurimas.demo.statistics.service.StatisticsService;
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
    private final StatisticsService statisticsService;

    public FileArchiveController(FileArchiveService fileArchiveService, StatisticsService statisticsService) {
        this.fileArchiveService = fileArchiveService;
        this.statisticsService = statisticsService;
    }

    @PostMapping(value = {"/archive", "/archive/{type}"})
    public ResponseEntity<StreamingResponseBody> archiveFiles(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              @RequestParam("file") MultipartFile[] files,
                                                              @PathVariable("type") Optional<String> type) throws IOException {

        final String archiveMethod = type.orElse(ZipArchiver.METHOD_ZIP);
        final StreamingResponseBody responseBody = fileArchiveService.archiveFiles(files, archiveMethod, response.getOutputStream());

        String name = "archive" + "." + fileArchiveService.getExtension(archiveMethod);
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "filename=" + name);

        statisticsService.logArchiveUsage(request.getRemoteAddr());

        return ResponseEntity.ok(responseBody);
    }
}

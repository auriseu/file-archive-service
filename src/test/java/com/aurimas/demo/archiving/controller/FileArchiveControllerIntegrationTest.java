package com.aurimas.demo.archiving.controller;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.aurimas.demo.FileArchiveServiceApplication;
import com.aurimas.demo.H2JpaConfig;
import com.aurimas.demo.statistics.dao.ArchiveLogDao;
import com.aurimas.demo.statistics.entity.ArchiveUsageLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FileArchiveServiceApplication.class, H2JpaConfig.class})
@AutoConfigureMockMvc
class FileArchiveControllerIntegrationTest {
    public static final String TEST_FILE_1_CONTENT = "Test File 1 Content";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArchiveLogDao logDao;

    private File responseZip;
    private File extractedFile;

    @AfterEach
    void tearDown() {
        if (Objects.nonNull(responseZip) && responseZip.exists()) {
            responseZip.delete();
        }

        if (Objects.nonNull(extractedFile) && extractedFile.exists()) {
            extractedFile.delete();
        }
    }

    @Test
    void shouldArchiveFilesToZipAndLogStatistics() throws Exception {
        assertThat("Log should not be present in DB", not(logDao.findByIp("0:0:0:0:0:0:0:1").isPresent()));

        MockMultipartFile file1 = new MockMultipartFile("file", "test-file-1.txt", "text/plain", TEST_FILE_1_CONTENT.getBytes());

        final MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/files/archive/zip")
                        .file(file1))
                .andExpect(status().isOk())
                .andReturn();

        responseZip = new File("response.zip");
        try (FileOutputStream out = new FileOutputStream(responseZip)) {
            StreamUtils.copy(new ByteArrayInputStream(response.getResponse().getContentAsByteArray()), out);
        }

        assertFile();
        assertLog();
    }

    private void assertFile() throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(responseZip))) {
            assertThat("Entry should be available", zis.available(), equalTo(1));

            ZipEntry zipEntry = zis.getNextEntry();
            assertThat(zipEntry.getName(), equalTo("test-file-1.txt"));

            extractedFile = new File(zipEntry.getName());
            assertThat("Extracted file should not be a directory", not(zipEntry.isDirectory()));

            try (FileOutputStream fos = new FileOutputStream(extractedFile)) {
                StreamUtils.copy(new ByteArrayInputStream(zis.readAllBytes()), fos);
            }

            assertThat(Files.readString(extractedFile.toPath()), equalTo(TEST_FILE_1_CONTENT));
            assertThat("No more entries should be available", zis.available(), equalTo(0));
            zis.closeEntry();
        }
    }

    private void assertLog() {
        final Optional<ArchiveUsageLog> byIp = logDao.findByIp("127.0.0.1");
        assertThat("Log should present in DB", byIp.isPresent());
        assertThat(byIp.get().getUsageCount(), equalTo(1));
    }
}
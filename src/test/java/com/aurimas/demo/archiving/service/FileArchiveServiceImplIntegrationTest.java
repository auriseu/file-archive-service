package com.aurimas.demo.archiving.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.List;

import com.aurimas.demo.archiving.archivers.FileArchiver;
import com.aurimas.demo.archiving.archivers.ZipArchiver;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class FileArchiveServiceImplIntegrationTest {


    @Autowired
    private FileArchiveService service;

    @Autowired
    private List<FileArchiver> archivers;

    @Test
    void shouldContainAllArchivers() {
        assertThat(archivers, hasSize(1));
        assertThat(archivers.get(0), instanceOf(ZipArchiver.class));
    }

    @Test
    void shouldGetExtension() {
        assertThat(service.getExtension("zip"), equalTo("zip"));
    }
}
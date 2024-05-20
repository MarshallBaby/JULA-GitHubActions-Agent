package org.marshallbaby.julagithubactionsagent.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class TestFileWriter {

    @SneakyThrows
    public void writeTestFile(JavaFile javaFile) {

        String pathToSave = buildFileName(javaFile.getFilePath());
        log.info("Writing file to: [{}].", pathToSave);
        FileUtils.write(new File(pathToSave), javaFile.getTestPayload(), StandardCharsets.UTF_8);
    }

    private String buildFileName(String path) {

        return path.substring(0, path.length() - 5).replace("/main/", "/test/") + "Test.java";
    }

}

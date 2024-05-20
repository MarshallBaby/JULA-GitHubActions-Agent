package org.marshallbaby.julagithubactionsagent.service;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class RelatedFilesEnricher {

    @Value("${jula.root.path}")
    private String rootPath;

    public void enrichWithRelatedFiles(JavaFile javaFile, String packageName) {

        List<String> relatedFilesPayloads = findRelatedFiles(javaFile, packageName)
                .stream()
                .map(this::readFile)
                .toList();

        javaFile.setRelatedFilesPayloads(relatedFilesPayloads);
    }

    private List<String> findRelatedFiles(JavaFile javaFile, String packageName) {

        return Arrays.stream(javaFile.getPayload().split("\n"))
                .filter(line -> isImportOfRelatedFile(line.trim(), packageName))
                .map(this::transformImportToFilePath)
                .toList();
    }

    private boolean isImportOfRelatedFile(String line, String packageName) {

        return line.startsWith("import ") && line.contains(packageName);
    }

    private String transformImportToFilePath(String importLine) {

        String path = extractReference(importLine).replace('.', '/') + ".java";
        return rootPath + "/src/main/java/" + path;
    }

    private String extractReference(String importLine) {

        String[] lineParts = importLine.split(" ");
        if (lineParts.length != 2) {
            String message = format("Invalid import line format: [%s].", importLine);
            throw new RuntimeException(message);
        }

        return lineParts[1].substring(0, lineParts[1].length() - 1);
    }

    @SneakyThrows
    private String readFile(String path) {

        return FileUtils.readFileToString(new File(path), UTF_8);
    }

}

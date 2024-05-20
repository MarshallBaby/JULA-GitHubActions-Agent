package org.marshallbaby.julagithubactionsagent.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JavaFilesCollector {

    @Value("${jula.required.files.path}")
    private String requiredFilesPath;

    public List<JavaFile> collectFiles() {

        return getRequiredFiles()
                .stream()
                .peek(file -> log.info("Test will be generated for file: [{}].", file))
                .map(JavaFile::new)
                .toList();
    }

    @SneakyThrows
    private List<String> getRequiredFiles() {

        List<String> files = new ArrayList<>();
        Reader reader = new BufferedReader(new FileReader(requiredFilesPath));
        IOUtils.lineIterator(reader).forEachRemaining(files::add);

        return files;
    }

}

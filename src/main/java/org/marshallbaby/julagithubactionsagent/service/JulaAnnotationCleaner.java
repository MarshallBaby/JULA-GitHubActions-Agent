package org.marshallbaby.julagithubactionsagent.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JulaAnnotationCleaner {

    private static final String JULA_ANNOTATION = "@JULA:";

    @SneakyThrows
    public void cleanJulaAnnotation(JavaFile javaFile) {

        String cleanedPayload = Arrays.stream(javaFile.getPayload().split("\n"))
                .filter(line -> !line.contains(JULA_ANNOTATION))
                .collect(Collectors.joining("\n"));

        FileUtils.write(new File(javaFile.getFilePath()), cleanedPayload, StandardCharsets.UTF_8);
    }

}

package org.marshallbaby.julagithubactionsagent.service;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Component
public class PayloadEnricher {

    @SneakyThrows
    public void enrichWithPayload(JavaFile javaFile) {

        String path = javaFile.getFilePath();
        String payload = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);

        javaFile.setPayload(payload);
    }

}

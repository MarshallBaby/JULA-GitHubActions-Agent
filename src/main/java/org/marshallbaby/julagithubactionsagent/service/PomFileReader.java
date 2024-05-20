package org.marshallbaby.julagithubactionsagent.service;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class PomFileReader {

    @Value("${jula.root.path}")
    private String rootPath;

    @SneakyThrows
    public String readPomFile() {

        return FileUtils.readFileToString(new File(rootPath + "/pom.xml"), UTF_8);
    }

}

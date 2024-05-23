package org.marshallbaby.julagithubactionsagent.step.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.marshallbaby.julagithubactionsagent.service.JulaProcessor;
import org.marshallbaby.julagithubactionsagent.step.Step;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(30)
@Component
@RequiredArgsConstructor
public class ProcessFilesStep implements Step {

    private final JulaProcessor julaProcessor;

    @Override
    public void process(List<JavaFile> javaFiles) {

        log.info("[3/5] Processing files ...");
        javaFiles.parallelStream()
                .peek(jf -> log.info("---> Processing file: [{}].", jf.getFilePath()))
                .forEach(julaProcessor::process);
    }
}

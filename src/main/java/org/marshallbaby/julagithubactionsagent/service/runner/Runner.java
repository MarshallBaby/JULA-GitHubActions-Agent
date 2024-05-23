package org.marshallbaby.julagithubactionsagent.service.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.marshallbaby.julagithubactionsagent.service.JavaFilesCollector;
import org.marshallbaby.julagithubactionsagent.step.Step;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Runner {

    private final JavaFilesCollector javaFilesCollector;
    private final List<Step> steps;

    public void run() {

        List<JavaFile> javaFiles = javaFilesCollector.collectFiles();

        log.info("STARTING JOB");
        steps.forEach(step -> step.process(javaFiles));
        log.info("JOB COMPLETE!");
    }

}

package org.marshallbaby.julagithubactionsagent.step.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.marshallbaby.julagithubactionsagent.service.TestFileWriter;
import org.marshallbaby.julagithubactionsagent.step.Step;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(40)
@Component
@RequiredArgsConstructor
public class WriteTestFileStep implements Step {

    private final TestFileWriter testFileWriter;

    @Override
    public void process(List<JavaFile> javaFiles) {

        log.info("[4/5] Writing test files.");
        javaFiles.forEach(testFileWriter::writeTestFile);
    }
}

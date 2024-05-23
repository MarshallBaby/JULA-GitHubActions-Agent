package org.marshallbaby.julagithubactionsagent.step.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.marshallbaby.julagithubactionsagent.service.JulaAnnotationCleaner;
import org.marshallbaby.julagithubactionsagent.step.Step;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(50)
@Component
@RequiredArgsConstructor
public class CleanJulaAnnotationStep implements Step {

    private final JulaAnnotationCleaner julaAnnotationCleaner;

    @Override
    public void process(List<JavaFile> javaFiles) {

        log.info("[5/5] Cleaning-up Jula annotations.");
        javaFiles.forEach(julaAnnotationCleaner::cleanJulaAnnotation);
    }
}

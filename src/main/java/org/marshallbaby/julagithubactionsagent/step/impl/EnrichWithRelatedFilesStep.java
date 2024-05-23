package org.marshallbaby.julagithubactionsagent.step.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.marshallbaby.julagithubactionsagent.service.PackageNameBuilder;
import org.marshallbaby.julagithubactionsagent.service.RelatedFilesEnricher;
import org.marshallbaby.julagithubactionsagent.step.Step;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(20)
@Component
@RequiredArgsConstructor
public class EnrichWithRelatedFilesStep implements Step {

    private final PackageNameBuilder packageNameBuilder;
    private final RelatedFilesEnricher relatedFilesEnricher;

    @Override
    public void process(List<JavaFile> javaFiles) {

        log.info("[2/5] Reading related Java files.");
        String packageName = packageNameBuilder.buildPackageName();
        javaFiles.forEach(javaFile -> relatedFilesEnricher.enrichWithRelatedFiles(javaFile, packageName));
    }
}

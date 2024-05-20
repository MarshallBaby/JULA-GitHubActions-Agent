package org.marshallbaby.julagithubactionsagent.runner;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.marshallbaby.julagithubactionsagent.service.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class Runner {

    private final JavaFilesCollector javaFilesCollector;
    private final PackageNameBuilder packageNameBuilder;
    private final PayloadEnricher payloadEnricher;
    private final RelatedFilesEnricher relatedFilesEnricher;
    private final JulaProcessor julaProcessor;
    private final TestFileWriter testFileWriter;

    public void run() {

        List<JavaFile> javaFiles = javaFilesCollector.collectFiles();

        enrichWithPayloads(javaFiles);
        enrichWithRelatedFiles(javaFiles);

        process(javaFiles);

        javaFiles.forEach(testFileWriter::writeTestFile);
    }

    private void enrichWithPayloads(List<JavaFile> javaFiles) {

        javaFiles.forEach(payloadEnricher::enrichWithPayload);
    }

    private void enrichWithRelatedFiles(List<JavaFile> javaFiles) {

        String packageName = packageNameBuilder.buildPackageName();
        javaFiles.forEach(javaFile -> relatedFilesEnricher.enrichWithRelatedFiles(javaFile, packageName));
    }

    private void process(List<JavaFile> javaFiles) {

        javaFiles.parallelStream().forEach(julaProcessor::process);
    }

}

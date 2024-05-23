package org.marshallbaby.julagithubactionsagent.step.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.marshallbaby.julagithubactionsagent.service.PayloadEnricher;
import org.marshallbaby.julagithubactionsagent.step.Step;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(10)
@Component
@RequiredArgsConstructor
public class EnrichWithPayloadsStep implements Step {

    private final PayloadEnricher payloadEnricher;

    @Override
    public void process(List<JavaFile> javaFiles) {

        log.info("[1/5] Reading files.");
        javaFiles.forEach(payloadEnricher::enrichWithPayload);
    }
}

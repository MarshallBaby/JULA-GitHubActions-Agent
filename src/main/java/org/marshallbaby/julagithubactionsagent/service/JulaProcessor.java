package org.marshallbaby.julagithubactionsagent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.domain.JavaFile;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JulaProcessor {

    private final PomFileReader pomFileReader;
    private final ChatClient julaChatClient;

    @Value("classpath:/template/prompt-template.st")
    private Resource promptTemplate;

    public void process(JavaFile javaFile) {

        PromptTemplate template = new PromptTemplate(promptTemplate);
        Prompt prompt = template.create(Map.of(
                "javafile", javaFile.getPayload(),
                "pomfile", pomFileReader.readPomFile(),
                "relatedfiles", formatRelatedFiles(javaFile.getRelatedFilesPayloads())
        ));

        log.info("Calling LLM engine for file: [{}].", javaFile.getFilePath());
        ChatResponse chatResponse = julaChatClient.call(prompt);
        log.info("Received response from LLM engine for file: [{}].", javaFile.getFilePath());
        javaFile.setTestPayload(chatResponse.getResult().getOutput().getContent());
    }

    private String formatRelatedFiles(List<String> relatedFiles) {

        StringBuilder stringBuilder = new StringBuilder();
        relatedFiles.forEach(file -> stringBuilder.append(file).append("\n\n"));
        return stringBuilder.toString();
    }

}

package org.marshallbaby.julagithubactionsagent.service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class JulaProcessor {

    private final PomFileReader pomFileReader;
    private final ChatClient julaChatClient;

    @Value("classpath:/template/prompt-template.st")
    private Resource promptTemplate;

    public void process(JavaFile javaFile) {

        System.out.println();

        PromptTemplate template = new PromptTemplate(promptTemplate);
        Prompt prompt = template.create(Map.of(
                "javafile", javaFile.getPayload(),
                "pomfile", pomFileReader.readPomFile(),
                "relatedfiles", formatRelatedFiles(javaFile.getRelatedFilesPayloads())
        ));

        ChatResponse chatResponse = julaChatClient.call(prompt);
        javaFile.setTestPayload(chatResponse.getResult().getOutput().getContent());

        System.out.println(javaFile);
    }

    private String formatRelatedFiles(List<String> relatedFiles) {

        StringBuilder stringBuilder = new StringBuilder();
        relatedFiles.forEach(file -> stringBuilder.append(file).append("\n\n"));
        return stringBuilder.toString();
    }

}

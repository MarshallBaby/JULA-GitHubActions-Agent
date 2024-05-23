package org.marshallbaby.julagithubactionsagent.connector.chat;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.julagithubactionsagent.connector.service.JulaConnectorService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class JulaChatClient implements ChatClient {

    private final JulaConnectorService julaConnectorService;

    @Override
    public ChatResponse call(Prompt prompt) {

        String query = prompt.getContents();

        UUID taskId = julaConnectorService.publishQuery(query);
        String response = julaConnectorService.receiveResponse(taskId);

        return new ChatResponse(singletonList(new Generation(response)));
    }
}

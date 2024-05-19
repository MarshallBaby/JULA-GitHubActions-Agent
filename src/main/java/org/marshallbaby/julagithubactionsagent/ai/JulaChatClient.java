package org.marshallbaby.julagithubactionsagent.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JulaChatClient implements ChatClient {
    @Override
    public ChatResponse call(Prompt prompt) {
        return null;
    }
}

package org.marshallbaby.julagithubactionsagent.connector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.connector.client.JulaConnectorClient;
import org.marshallbaby.julagithubactionsagent.domain.Task;
import org.marshallbaby.julagithubactionsagent.domain.TaskResponse;
import org.marshallbaby.julagithubactionsagent.exception.TaskIncompleteException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JulaConnectorService {

    private static final String TASK_COMPLETE_STATUS = "COMPLETE";

    private final JulaConnectorClient julaConnectorClient;

    public UUID publishQuery(String query) {

        log.info("Pushing query: [{}] to the queue", query.substring(0, 30));

        Task task = buildTask(query);
        String uuid = julaConnectorClient.publishTask(task);
        return UUID.fromString(uuid);
    }

    @Retryable(
            retryFor = {TaskIncompleteException.class},
            maxAttempts = 150,
            backoff = @Backoff(
                    delay = 2000
            )
    )
    public String receiveResponse(UUID taskId) {

        log.info("Fetching response: [{}].", taskId);

        TaskResponse taskResponse = julaConnectorClient.getTaskResponse(taskId);

        if (TASK_COMPLETE_STATUS.equalsIgnoreCase(taskResponse.getMessage())) {

            return taskResponse.getPayload();

        } else {

            throw new TaskIncompleteException();
        }
    }


    private Task buildTask(String query) {

        return Task.builder()
                .requestPayload(query)
                .build();
    }
}
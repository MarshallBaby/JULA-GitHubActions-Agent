package org.marshallbaby.julagithubactionsagent.ai;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.julagithubactionsagent.client.JulaConnectorClient;
import org.marshallbaby.julagithubactionsagent.domain.Task;
import org.marshallbaby.julagithubactionsagent.domain.TaskResponse;
import org.marshallbaby.julagithubactionsagent.exception.TaskIncompleteException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JulaConntectorService {

    private static final String TASK_COMPLETE_STATUS = "COMPLETE";

    private final JulaConnectorClient julaConnectorClient;

    public UUID publishQuery(String query) {

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

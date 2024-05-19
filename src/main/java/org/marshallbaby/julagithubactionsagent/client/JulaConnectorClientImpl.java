package org.marshallbaby.julagithubactionsagent.client;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.julagithubactionsagent.domain.Task;
import org.marshallbaby.julagithubactionsagent.domain.TaskResponse;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
public class JulaConnectorClientImpl implements JulaConnectorClient {

    private static final String POST_TASK_URL = "/api/task";
    private static final String GET_RESPONSE_URL = "/api/task/{taskId}";

    private final RestTemplate julaConnectorRestTemplate;

    @Override
    public String publishTask(Task task) {
        return julaConnectorRestTemplate.postForObject(POST_TASK_URL, task, String.class);
    }

    @Override
    public TaskResponse getTaskResponse(UUID taskId) {
        return julaConnectorRestTemplate.getForObject(GET_RESPONSE_URL, TaskResponse.class, taskId);
    }
}

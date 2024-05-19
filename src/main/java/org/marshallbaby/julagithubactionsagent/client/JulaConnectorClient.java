package org.marshallbaby.julagithubactionsagent.client;

import org.marshallbaby.julagithubactionsagent.domain.Task;
import org.marshallbaby.julagithubactionsagent.domain.TaskResponse;

import java.util.UUID;

public interface JulaConnectorClient {

    String publishTask(Task task);

    TaskResponse getTaskResponse(UUID taskId);

}

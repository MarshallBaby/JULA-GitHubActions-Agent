package org.marshallbaby.julagithubactionsagent.domain;

import lombok.Data;

import java.util.List;

@Data
public class JavaFile {

    private String payload;
    private String filePath;
    private List<String> relatedFilesPayloads;
    private String testPayload;

    public JavaFile(String filePath) {
        this.filePath = filePath;
    }
}

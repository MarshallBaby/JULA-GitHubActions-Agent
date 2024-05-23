package org.marshallbaby.julagithubactionsagent.step;

import org.marshallbaby.julagithubactionsagent.domain.JavaFile;

import java.util.List;

public interface Step {
    void process(List<JavaFile> javaFiles);
}

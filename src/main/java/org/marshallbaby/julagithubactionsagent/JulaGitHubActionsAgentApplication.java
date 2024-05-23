package org.marshallbaby.julagithubactionsagent;

import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.julagithubactionsagent.service.runner.Runner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class JulaGitHubActionsAgentApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(JulaGitHubActionsAgentApplication.class, args);
        Runner runner = context.getBean(Runner.class);
        int exitCode = run(runner);
        exit(context, exitCode);
    }

    private static int run(Runner runner) {

        try {

            runner.run();
            return 0;

        } catch (Throwable throwable) {

            log.error("ERROR: [{}].", throwable.getMessage(), throwable);
            return 1;
        }
    }

    private static void exit(ApplicationContext context, int exitCode) {

        SpringApplication.exit(context, () -> exitCode);
        System.exit(exitCode);
    }

}

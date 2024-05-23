package org.marshallbaby.julagithubactionsagent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Component
public class PackageNameBuilder {

    private static final String INVALID_PROJECT_STRUCTURE_ERROR_MESSAGE
            = "Unable to determine project package name: invalid project tree.";
    private static final String SRC_PATH = "/src/main/java";

    @Value("${jula.root.path}")
    private String rootPath;

    public String buildPackageName() {

        StringBuilder packageName = new StringBuilder();
        File currentDirectory = new File(rootPath + SRC_PATH);

        while (true) {

            File[] filesInCurrentDirectory = getFiles(currentDirectory);

            if (filesInCurrentDirectory.length == 1 && filesInCurrentDirectory[0].isDirectory()) {

                packageName.append(filesInCurrentDirectory[0].getName()).append(".");
                currentDirectory = filesInCurrentDirectory[0];

            } else {

                validateJavaRootFileExists(filesInCurrentDirectory);
                return packageName.substring(0, packageName.length() - 1);
            }
        }
    }

    private void validateJavaRootFileExists(File[] files) {

        Arrays.stream(files)
                .filter(File::isFile)
                .filter(this::isJavaFile)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(INVALID_PROJECT_STRUCTURE_ERROR_MESSAGE));
    }

    private boolean isJavaFile(File file) {

        return file.getAbsolutePath().endsWith(".java");
    }

    private File[] getFiles(File dir) {

        return Objects.requireNonNull(dir.listFiles());
    }

}

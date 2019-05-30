package org.markbr.simplex.generator.source;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public class FilePathSource implements Source {
    private Path pathToYaml;

    public FilePathSource(Path pathToYaml) {
        this.pathToYaml = pathToYaml;
    }

    @Override
    public String getContent() {

        // Read yaml content from file
        try {
            return FileUtils.readFileToString(pathToYaml.toFile(), "UTF-8");
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read yaml source file", e);
        }
    }
}

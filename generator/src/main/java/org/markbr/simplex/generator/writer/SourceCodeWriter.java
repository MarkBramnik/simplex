package org.markbr.simplex.generator.writer;

import java.nio.file.Path;

public interface SourceCodeWriter {
    void write(Path baseDir, String packageName, String fileName, String sourceCode);
}

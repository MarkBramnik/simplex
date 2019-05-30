package org.markbr.simplex.generator.writer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SourceCodeWriterImpl implements SourceCodeWriter {

    private static final String SEPARATOR_STRING = new String(new char [] {File.separatorChar});
    private static final Charset ENCODING = Charset.forName("UTF-8");
    private static final boolean IS_WINDOWS = System.getProperty( "os.name" ).contains( "indow" );

    @Override
    public void write(Path baseDir, String packageName, String fileName, String sourceCode) {
        String [ ] folders = packageName.split("\\.");
        Path pathToExceptionsPack = Paths.get(baseDir.toString(), folders);
        String fullPathToExceptionsPackage = pathToExceptionsPack.toString();//baseDir.toFile().getAbsolutePath() + SEPARATOR_STRING + packagePathString;
        File dirToPlaceExceptionsIn = new File(fullPathToExceptionsPackage);
        if(!dirToPlaceExceptionsIn.exists()) {
            boolean result = dirToPlaceExceptionsIn.mkdirs();
            if(!result) {
                throw new IllegalArgumentException("Failed to create directories for path: " + fullPathToExceptionsPackage);
            }
        }
        if(!dirToPlaceExceptionsIn.isDirectory()) {
            throw new IllegalArgumentException("The path : " + fullPathToExceptionsPackage + " must be a directory");
        }

        File fileToWriteTo = new File(dirToPlaceExceptionsIn, fileName);
        String pathToWriteTo = fileToWriteTo.getAbsolutePath();
        if(fileToWriteTo.exists()) {
           boolean result = fileToWriteTo.delete();
           if(!result) {
               throw new IllegalArgumentException("File " + pathToWriteTo + " exists and could not be overriden");
           }
        }
        try {
            FileUtils.write(fileToWriteTo, sourceCode, ENCODING);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to write file: " +pathToWriteTo, e);
        }
    }

    private String toAbsolutePathString(Path path) {
        String rawFullPath = path.toString();
        if(IS_WINDOWS) {
            return rawFullPath.substring(1);
        }
        else {
            return rawFullPath;
        }
    }
}

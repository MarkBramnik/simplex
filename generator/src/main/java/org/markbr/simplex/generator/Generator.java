package org.markbr.simplex.generator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.AllArgsConstructor;
import org.markbr.simplex.generator.data.Metadata;
import org.markbr.simplex.generator.data.RawYamlEntries;
import org.markbr.simplex.generator.data.RawYamlEntry;
import org.markbr.simplex.generator.di.GeneratorDIModule;
import org.markbr.simplex.generator.parser.ParsedExceptionData;
import org.markbr.simplex.generator.parser.RawEntryParser;
import org.markbr.simplex.generator.parser.RawYamlReader;
import org.markbr.simplex.generator.source.FilePathSource;
import org.markbr.simplex.generator.source.Source;
import org.markbr.simplex.generator.sourcecode.SourceCodeBaseClassGenerator;
import org.markbr.simplex.generator.sourcecode.SourceCodeGenerator;
import org.markbr.simplex.generator.writer.SourceCodeWriter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;

@AllArgsConstructor(onConstructor = @__({ @Inject }))
public class Generator {
    private RawYamlReader yamlReader;
    private RawEntryParser rawEntryParser;
    private SourceCodeGenerator sourceCodeGenerator;
    private SourceCodeBaseClassGenerator sourceCodeBaseClassGenerator;
    private SourceCodeWriter sourceCodeWriter;


    public void generate(Metadata metadata, Source source, Path outputFolder) {
        // Generate exceptions code

        RawYamlEntries rawData = yamlReader.readYaml(source);

        processBaseException(metadata, outputFolder);

        for(RawYamlEntry entry : rawData) {

            processSingleEntry(metadata, entry, outputFolder);

        }

    }

    private void processBaseException(Metadata metadata, Path outputFolder) {
        if(metadata.isGenerateBaseClass()) {

            String sourceCode = generateSourceCodeForBaseException(metadata.getBaseExceptionPackageName(), metadata.getBaseExceptionClassName());

            writeGeneratedExceptionToDisk(sourceCode, outputFolder, metadata.getBaseExceptionPackageName(), metadata.getBaseExceptionClassName());
        }
    }



    private void processSingleEntry(Metadata metadata, RawYamlEntry entry, Path outputFolder) {

        ParsedExceptionData request = parseYamlEntry(metadata, entry);

        String  sourceCode = generateSourceCodeForException(request);

        writeGeneratedExceptionToDisk(sourceCode, outputFolder, metadata.getExceptionsPackageName(), request.getExceptionClassName());

    }

    private String generateSourceCodeForException(ParsedExceptionData request) {
        return sourceCodeGenerator.generateSourceCode(request);
    }

    private String generateSourceCodeForBaseException(String baseExceptionPackageName, String baseExceptionClassName) {
        return sourceCodeBaseClassGenerator.generateBaseClassSourceCode(baseExceptionPackageName, baseExceptionClassName);
    }

    private ParsedExceptionData parseYamlEntry(Metadata metadata, RawYamlEntry entry) {
        return rawEntryParser.parseData(metadata, entry);
    }

    private void writeGeneratedExceptionToDisk(String sourceCode, Path outputFolder, String packageName, String fileName) {
        sourceCodeWriter.write(outputFolder, packageName, fileName + ".java", sourceCode);
    }


    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        Injector injector = Guice.createInjector(new GeneratorDIModule());
        Generator generator = injector.getInstance(Generator.class);
        Metadata metadata = new Metadata("com.markbr.simplex.exceptions", "com.markbr.simplex.base", "BaseSimplexException", true);
        Path pathToYaml   = new File("C:\\devl\\work\\personal\\simplex\\generator\\src\\test\\resources\\sampleInput.yaml").toPath();
        Path outputFolder = new File("C:\\devl\\work\\personal\\simplex\\generator\\target\\generated-sources").toPath();
        generator.generate(metadata,
                           new FilePathSource(pathToYaml),
                           outputFolder);

    }


}

package org.markbr.simplex.generator.maven.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import  org.apache.maven.plugin.logging.Log;
import org.markbr.simplex.generator.Generator;
import org.markbr.simplex.generator.data.Metadata;
import org.markbr.simplex.generator.di.GeneratorDIModule;
import org.markbr.simplex.generator.source.FilePathSource;

import java.io.File;
import java.nio.file.Path;

public class GeneratorInvoker {

    private String inputFile;
    private String outputDir;
    private boolean generateBaseException;
    private String baseExceptionPackage;
    private String baseExceptionName;
    private String exceptionsPackage;
    private Log logger;

    public GeneratorInvoker(String inputFile, String outputDir, boolean generateBaseException, String baseExceptionPackage, String baseExceptionName, String exceptionsPackage, Log logger,
                            String projectDir, String projectBuildDir) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
        this.generateBaseException = generateBaseException;
        this.baseExceptionPackage = baseExceptionPackage;
        this.baseExceptionName = baseExceptionName;
        this.exceptionsPackage = exceptionsPackage;
        this.logger = logger;
        if(isEmpty(baseExceptionPackage)) {
            this.baseExceptionPackage = "java.lang";
        }
        if (isEmpty(baseExceptionName)) {
            this.baseExceptionName = "RuntimeException";
        }
        if(isEmpty(this.inputFile)) {
            this.inputFile = projectDir + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources" + File.separatorChar + "simplex-exceptions.yml";
        }
        if(isEmpty(outputDir)) {
            this.outputDir = projectBuildDir + File.separatorChar + "generated-sources";
        }
    }

    public void invoke() {

        printParameters();


        Metadata metadata = new Metadata(exceptionsPackage, baseExceptionPackage, baseExceptionName, generateBaseException);
        Path pathToYaml = new File(inputFile).toPath();
        Path outputFolder = new File(outputDir).toPath();


        Injector injector = Guice.createInjector(new GeneratorDIModule());
        Generator generator = injector.getInstance(Generator.class);
        //Metadata metadata = new Metadata("com.markbr.simplex.exceptions", "com.markbr.simplex.base", "BaseSimplexException", true);
        //Path pathToYaml   = new File("C:\\devl\\work\\personal\\simplex\\generator\\src\\test\\resources\\sampleInput.yaml").toPath();
        //Path outputFolder = new File("C:\\devl\\work\\personal\\simplex\\generator\\target\\generated-sources").toPath();
        try {
            generator.generate(metadata,
                    new FilePathSource(pathToYaml),
                    outputFolder);
        }catch (Exception ex) {

        }
    }

    public String getOutputDir() {
        return outputDir;
    }

    private boolean isEmpty(String str) {
        return (str == null) || ("".equals(str));
    }

    private void printParameters() {

        logger.info("====================================================");
        logger.info("== Simplex started with the following parameters:");
        logger.info("== Input File (YAML): "  + inputFile);
        logger.info("== Output Directory: " + outputDir);
        logger.info("== Generate Base Exception: " + generateBaseException);
        logger.info("== Base Exception will belong to Package: " + baseExceptionPackage);
        logger.info("== Base Exception will be named: " + baseExceptionName);
        logger.info("== Exceptions will belong to Package: " + exceptionsPackage);
        logger.info("====================================================");


    }

}

package org.markbr.simplex.generator.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo( name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GenerateMojo extends AbstractMojo {


    @Parameter(name = "inputFile", required = false)
    private String inputFile;

    @Parameter(name = "generateBaseException", defaultValue = "true")
    private  boolean generateBaseException;

    @Parameter(name = "baseExceptionPackage", required = false)
    private String baseExceptionPackage;

    @Parameter(name = "baseExceptionName", required = false, defaultValue = "BaseSimplexException")
    private String baseExceptionName;

    @Parameter(name = "exceptionsPackage", required = true)
    private String exceptionsPackage;

    @Parameter(name = "outputDir", required = false)
    private String outputDir;



    @Parameter(property = "project.build.directory")
    private String projectBuildDirectory;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Simplex - Exception Generation Framework");
        GeneratorInvoker invoker = new GeneratorInvoker(
                inputFile,
                outputDir,
                generateBaseException,
                baseExceptionPackage,
                baseExceptionName,
                exceptionsPackage,
                getLog(),
                project.getBasedir().getAbsolutePath(),
                projectBuildDirectory
        );
        invoker.invoke();
        String outputDir = invoker.getOutputDir();
        project.addCompileSourceRoot(outputDir);
    }


}

package org.markbr.simplex.generator.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.markbr.simplex.generator.Generator;
import org.markbr.simplex.generator.parser.RawYamlReader;
import org.markbr.simplex.generator.parser.RawYamlReaderImpl;
import org.markbr.simplex.generator.parser.RawEntryParser;
import org.markbr.simplex.generator.parser.RawEntryParserImpl;
import org.markbr.simplex.generator.sourcecode.SourceCodeBaseClassGenerator;
import org.markbr.simplex.generator.sourcecode.SourceCodeBaseExceptionClassGeneratorImpl;
import org.markbr.simplex.generator.sourcecode.SourceCodeGenerator;
import org.markbr.simplex.generator.sourcecode.SourceCodeRealExceptionGeneratorImpl;
import org.markbr.simplex.generator.writer.SourceCodeWriter;
import org.markbr.simplex.generator.writer.SourceCodeWriterImpl;

public class GeneratorDIModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Generator.class).in(Scopes.SINGLETON);
        bind(RawYamlReader.class).to(RawYamlReaderImpl.class).in(Scopes.SINGLETON);
        bind(RawEntryParser.class).to(RawEntryParserImpl.class).in(Scopes.SINGLETON);
        bind(SourceCodeBaseClassGenerator.class).to(SourceCodeBaseExceptionClassGeneratorImpl.class).in(Scopes.SINGLETON);
        bind(SourceCodeWriter.class).to(SourceCodeWriterImpl.class).in(Scopes.SINGLETON);
        bind(SourceCodeGenerator.class).to(SourceCodeRealExceptionGeneratorImpl.class).in(Scopes.SINGLETON);
    }
}

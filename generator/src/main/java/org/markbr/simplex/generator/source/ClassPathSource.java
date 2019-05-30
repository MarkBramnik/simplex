package org.markbr.simplex.generator.source;



import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ClassPathSource implements Source{
    private String pathToSource;

    public ClassPathSource(String pathToSource) {
        this.pathToSource = pathToSource;
    }

    @Override
    public String getContent() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(pathToSource);
        try {
            return IOUtils.toString(is, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

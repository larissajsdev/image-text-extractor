package cem.celodev.tesseract.config;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import jakarta.servlet.ServletContext;

@Configuration
public class PathConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Qualifier("appDirBean")
    String appDirBean() {
        return Path.of(System.getProperty("user.dir"), "tessdata").toString();
    }
}

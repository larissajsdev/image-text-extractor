package cem.celodev.tesseract.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import net.sourceforge.tess4j.Tesseract;

@Configuration
public class TesseractConfig {

    @Autowired
    private Environment environment;

  
    @Value("${tesseract.language}")
    private String TESSERACT_LANGUAGE;

    @Value("#{appDirBean}")
    @Qualifier("appDirBean")
    private String PATH_TESSDATA;

    @Bean
     Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        System.out.println(environment.getProperty("tesseract.data.path"));
        tesseract.setDatapath(environment.getProperty("tesseract.data.path"));
        tesseract.setLanguage(environment.getProperty("tesseract.data.language"));
        tesseract.setTessVariable("user_defined_dpi", environment.getProperty("tesseract.data.dpi"));
        return tesseract;
    }
}
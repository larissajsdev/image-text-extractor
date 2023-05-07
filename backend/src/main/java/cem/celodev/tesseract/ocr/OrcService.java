package cem.celodev.tesseract.ocr;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OrcService {

    @Autowired private Tesseract tesseract;

    public String extractTextFromImg(InputStream inputStream) throws IOException, TesseractException {
        try {
            return tesseract.doOCR(ImageIO.read(inputStream));
        } catch (IOException e) {
            throw new IOException();
        } catch (TesseractException e) {
            throw new TesseractException();
        }
    }
}

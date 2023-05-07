package cem.celodev.tesseract.ocr;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.TesseractException;

@RestController
@RequestMapping("api/ocr")
public class OcrResource {
    
    @Autowired OrcService service;

    @PostMapping("image-text")
    public ResponseEntity<String> extractTextFromImg(
            @RequestBody MultipartFile img) throws IOException, TesseractException {
        return ResponseEntity.ok(service.extractTextFromImg(img.getInputStream()));
    }
}

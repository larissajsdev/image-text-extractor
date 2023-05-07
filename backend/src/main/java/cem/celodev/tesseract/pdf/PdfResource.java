package cem.celodev.tesseract.pdf;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cem.celodev.tesseract.pdf.dto.TextPageDTO;
import net.sourceforge.tess4j.TesseractException;

@RestController
@RequestMapping("api/pdf")
public class PdfResource {

    @Autowired
    PdfService service;

    @PostMapping("convert/image/{page}")
    public ResponseEntity<ByteArrayResource> convertPagePdfToImg(
            @RequestBody MultipartFile pdf,
            @PathVariable int page) throws IOException {
        return ResponseEntity.ok(service.convertPagePdfToImg(pdf.getInputStream(), page));
    }

    @PostMapping("convert/text")
    public ResponseEntity<List<TextPageDTO>> getTextPages(@RequestBody MultipartFile pdf) throws IOException, TesseractException{
        return ResponseEntity.ok(service.getTextPages(pdf.getInputStream()));
    }
}

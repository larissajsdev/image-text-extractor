package cem.celodev.tesseract.pdf;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cem.celodev.tesseract.config.TesseractConfig;
import cem.celodev.tesseract.ocr.OrcService;
import cem.celodev.tesseract.pdf.dto.TextPageDTO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class PdfService {

    @Autowired
    private OrcService orcService;

    public ByteArrayResource convertPagePdfToImg(InputStream pdfInputStream, int page) throws IOException {

        try {
            PDDocument pd = PDDocument.load(pdfInputStream);
            PDFRenderer pr = new PDFRenderer(pd);
            BufferedImage bi = pr.renderImageWithDPI(page, 300);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);

            return new ByteArrayResource(baos.toByteArray());
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public List<TextPageDTO> getTextPages(InputStream pdfInputStream) throws IOException, TesseractException {

        List<TextPageDTO> pages = new ArrayList<>();

        PDDocument pdfDocument = PDDocument.load(pdfInputStream);

        PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

        int TOTAL_PAGES = pdfDocument.getPages().getCount();

        for (int i = 10; i < 12; i++) {

            BufferedImage bi = pdfRenderer.renderImageWithDPI(i, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
            String extractedText = orcService.extractTextFromImg(inputStream);

            System.out.println(extractedText);
            pages.add(new TextPageDTO(i + 1, extractedText));

        }

        pdfDocument.close();

        return pages;
    }
}

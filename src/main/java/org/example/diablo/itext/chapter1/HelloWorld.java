package org.example.diablo.itext.chapter1;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class HelloWorld {
    public static void main(String[] args) throws FileNotFoundException {
        // 编写Pdf的对象
        PdfWriter pdfWriter = new PdfWriter("D:\\mystuff\\learn\\itext\\hello_world.pdf");
        // 要进行编写的pdf对象
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.add(new Paragraph("Hello World!"));
        document.close();
    }
}

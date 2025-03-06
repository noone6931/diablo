package org.example.diablo.itext.demo;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class Demo {

    static PdfFont font = null;

    public static final String DEST = "src/main/resources/itext/results/demo.pdf";
    public static final String FONT_PATH = "src/main/resources/itext/font/simhei.ttf";
    public static void main(String[] args) throws Exception {
        font = PdfFontFactory.createFont(FONT_PATH, PdfEncodings.IDENTITY_H, true);
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Demo().createPdf(DEST);
    }

    private void createPdf(String dest) throws Exception {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());

        Document document = new Document(pdf);
        // Add title
        Paragraph p = new Paragraph("List of reported UFO sightings in 20th century")
//                .set
                .setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(14);
        document.add(p);
        document.close();

    }


    protected class MyEventHandler implements IEventHandler{

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            // 创建页眉和页脚
            pdfCanvas.beginText()
                    .setFontAndSize(font, 18)
                    .moveText(pageSize.getWidth()/2-100, pageSize.getTop()-50)
                    .showText("电  子  商  业  承  兑  汇  票")
                    .moveText(60, -pageSize.getTop()+30)
                    .showText(String.valueOf(pageNumber))
                    .endText();

            // 添加显示日期
            pdfCanvas.beginText()
                    .setFontAndSize(font, 8)
                    .moveText(pageSize.getLeft() + 15, pageSize.getTop() - 10)  // 左对齐显示日期
                    .showText("显示日期：2025-01-10 10:46")
                    .endText();


            // 票据状态和号码
            pdfCanvas.beginText()
                    .setFontAndSize(font, 8)
                    .moveText(pageSize.getRight() - 190, pageSize.getTop() - 80)  // 右上角对齐
                    .showText("票据状态：已承兑-待收票")
                    .moveText(0, -15)  // 下一行
                    .showText("票据号码：7 313656000094 20241205 00005605 3")
                    .endText();

            pdfCanvas.release();
        }
    }
}

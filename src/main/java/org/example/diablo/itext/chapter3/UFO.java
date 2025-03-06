package org.example.diablo.itext.chapter3;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
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
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class UFO {
    public static final String DATA = "src/main/resources/data/ufo.csv";
    public static final String DEST = "src/main/resources/results/chapter03/ufo.pdf";

    static PdfFont helvetica = null;
    static PdfFont helveticaBold = null;

    public static void main(String[] args) throws Exception {
        helvetica = PdfFontFactory.createFont(FontConstants.HELVETICA);
        helveticaBold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new UFO().createPdf(DEST);
    }

    protected void createPdf(String dest) throws Exception {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());

        // Initialize document
        Document document = new Document(pdf);

        // Add title
        Paragraph p = new Paragraph("List of reported UFO sightings in 20th century")
                .setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(14);
        document.add(p);

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 5, 7, 4}));

        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        process(table, line, helveticaBold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, helvetica, false);
        }
        br.close();

        document.add(table);

        document.close();
    }

    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.BLACK, 0.5f)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.BLACK, 0.5f)));
            }
        }
    }


    protected class MyEventHandler implements IEventHandler {

        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();

            //page.newContentStreamBefore()：如果在页面渲染之后绘制一个不透明的矩形，那么这个矩形将覆盖现有内容，为此，需要访问之前添加的内容流，以便背景和水印不会覆盖原来表格中的内容。
            //page.getResources()：每个内容流引用的外部资源，比如字体或者图像。如果我们要添加新的内容到一个页面中，那么请确保iText有足够的权限访问这个页面的资源句柄。
            //pdfDoc： 我们需要访问一个PdfDocument对象，因为它可以代表添加了新内容后的新PDF对象。
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            //Set background
            /**
             * 定义了limeColor和blueColor两种颜色，保存了当前的图形状态后，根据页码将填充颜色更改为上述两种颜色中的一种，
             * 之后由创建了一个矩形并用选定的颜色填充它。这样做就会让石灰或者蓝色铺满整个页面，之后恢复图形状态并返回到原来填充的颜色上，因为我们不希望其他内容受到颜色变化的影响。
             */
            Color limeColor = new DeviceCmyk(0.208f, 0, 0.584f, 0);
            Color blueColor = new DeviceCmyk(0.445f, 0.0546f, 0, 0.0667f);
            pdfCanvas.saveState()
                    .setFillColor(pageNumber % 2 == 1 ? limeColor : blueColor)
                    .rectangle(pageSize.getLeft(), pageSize.getBottom(), pageSize.getWidth(), pageSize.getHeight())
                    .fill().restoreState();

            //Add header and footer
            /**
             * 我们创建了一个文本对象，设置好字体和字体大小之后，把它移动到靠近页面顶部的中间位置，然后写了一段“THE TRUTH IS OUT THERE”
             * ，之后把光标移动到页面底部，在那里写上了页码，最后关闭这个文本对象。通过这些步骤，页眉和页脚就添加到了页面之中。
             */
            pdfCanvas.beginText()
                    .setFontAndSize(helvetica, 9)
                    .moveText(pageSize.getWidth() / 2 - 60, pageSize.getTop() - 20)
                    .showText("THE TRUTH IS OUT THERE")
                    .moveText(60, -pageSize.getTop() + 30)
                    .showText(String.valueOf(pageNumber))
                    .endText();

            //Add watermark
            /**
             * 我们创建了一个名为canvas的Canvas对象，这样就可以通过setProperty()来更改字体、字体大小等属性，而不是用PDF的语法。
             */
            Canvas canvas = new Canvas(pdfCanvas, pdfDoc, page.getPageSize());
            canvas.setFontColor(Color.WHITE);
            canvas.setProperty(Property.FONT_SIZE, 60);
            canvas.setProperty(Property.FONT, helveticaBold);
            canvas.showTextAligned(new Paragraph("CONFIDENTIAL"),
                    298, 421, pdfDoc.getPageNumber(page),
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

        }
    }

}

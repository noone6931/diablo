package org.example.diablo.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class PngCompressor {
    public static void main(String[] args) {
        String inputImagePath = "C:\\Users\\CHENG\\Desktop\\87570ac364f6dd815aaf543cc2d2140.png";
        String outputImagePath = "C:\\Users\\CHENG\\Desktop\\test.jpg";
        float compressionQuality = 0.8f; // Adjust the quality value (0.0f - 1.0f)

        try {
            compressImage(inputImagePath, outputImagePath, compressionQuality);
        } catch (IOException e) {
            System.err.println("Error compressing the image: " + e.getMessage());
        }
    }

    public static void compressImage(String inputImagePath, String outputImagePath, float quality) throws IOException {
        // Read the original image
        BufferedImage image = ImageIO.read(new File(inputImagePath));

        // Convert image to RGB if it has an alpha channel (transparency)
        BufferedImage rgbImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g = rgbImage.createGraphics();
        g.drawImage(image, 0, 0, Color.WHITE, null);
        g.dispose();

        // Get a writer for JPEG format
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No writers found for JPEG format.");
        }
        ImageWriter writer = writers.next();
        // Set the output file format

        // Set the compression quality
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
        }

        // Write the compressed image to the output file
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(new File(outputImagePath))) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(rgbImage, null, null), param);
        }

        writer.dispose();
    }
}

package ru.shabarov.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int TARGET_IMAGE_WIDTH = 420;

    //TODO: @Value doesn't work
//    @Value("${blog.image.relativeImagePath}")
    private String relativeImagePath = "/images";

//    @Value("${blog.image.basePath}")
    private String basePath = "/tmp";

    public String saveImage(MultipartFile file) throws IOException {
        String pathToFile = "No image";
        if (file != null && !file.isEmpty()) {
            try (InputStream is = new ByteArrayInputStream(file.getBytes())) {
                BufferedImage src = ImageIO.read(is);
                pathToFile = String.format("%s/image-%d.png", relativeImagePath, new Date().getTime());
                final String fullImagePath = basePath + pathToFile;
                logger.info("Image path = {}", fullImagePath);
                File destination = new File(fullImagePath);
                BufferedImage resizedImage = resizeImage(src);
                ImageIO.write(resizedImage, "png", destination);
            }
        }
        return pathToFile;
    }

    private BufferedImage resizeImage(final BufferedImage image) {
        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();
        return toBufferedImage(image.getScaledInstance(TARGET_IMAGE_WIDTH, TARGET_IMAGE_WIDTH * oldHeight / oldWidth,
                Image.SCALE_DEFAULT));
    }

    private BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}

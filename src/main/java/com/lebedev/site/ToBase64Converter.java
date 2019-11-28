package com.lebedev.site;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ToBase64Converter {
    public static String convert(BufferedImage image) {
        String base64Image = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            base64Image = Base64.encodeBase64String(imageInByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }
}

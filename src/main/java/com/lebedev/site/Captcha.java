package com.lebedev.site;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Component
@PropertySource(value = "classpath:advanced.properties")
public class Captcha {

    @Value("${advanced.captchaSize}")
    int size = 6;
    @Value("${advanced.captcha}")
    private Boolean captchaON;
    private String value;
    private BufferedImage image;
    private String base64Image;
    private int width = 200;
    private int height = 70;


    public int getSize() {
        return size;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getBase64Image() {
        return base64Image;
    }

    @Override
    public String toString() {
        return value;
    }

    public Captcha() {
        value = valueGenerate();
        image = createImage();
        base64Image = toBase64(image);
    }

    private String valueGenerate() {
        value = "";
        if (size > 15) {
            size = 15;
        }
        for (int i = 1; i <= size; i++) {
            value += (new Random()).nextInt(10);
        }
        return value;
    }

    private BufferedImage createImage() {
        Random r = new Random();
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setFont(new Font("Calibri", Font.PLAIN, height / 3));
        graphics.setColor(new Color(Color.HSBtoRGB(r.nextFloat(), 1, 1)));
        float gr = r.nextFloat();
        graphics.rotate(gr, width / 2, height / 2);
        for (int i = 0; i < size; i++) {
            int wpos = (int) (width * 0.3 + height * ((double) i / size));
            int hpos = (int) (height * 0.7);
            float cr = r.nextFloat();
            graphics.rotate(-cr, width / 2, height / 2);
            graphics.drawString(Character.toString(value.charAt(i)), wpos, hpos);
            graphics.rotate(cr, width / 2, height / 2);
        }
        graphics.rotate(-gr, width / 2, height / 2);
        for (int i = 0; i < 7; i++) {
            graphics.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
        }
        return image;
    }

    private String toBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
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

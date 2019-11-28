package com.lebedev.site;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Captcha {

    int size = 6;
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
        base64Image = ToBase64Converter.convert(image);
    }

    private String valueGenerate() {
        value = "";
        if (size > 7) {
            size = 7;
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


    public static String base64PalmImage() {
        BufferedImage palm = null;
        try {
            File file = new File("src/main/resources/static/palm.png");
            palm = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ToBase64Converter.convert(palm);

    }
}

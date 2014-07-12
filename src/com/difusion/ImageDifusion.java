package com.difusion;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Clase para la difusión de una imagen en un grupo. Se envía a todos los
 * clientes del grupo
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class ImageDifusion implements java.io.Serializable {

    private byte image[];
    private String idGroup;
    private String idTransmitter;
    private String extension;
    private String name;

    public ImageDifusion(String idGroup) {
        this.idGroup = idGroup;
    }

    public void setImage(String url) {
        try {
            File file = new File(url);

            this.extension = getExtension(url);
            this.name = getName(file.getName());
            BufferedImage buffer = ImageIO.read(file);
            ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
            ImageIO.write(buffer, extension, imageByte);
            image = imageByte.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ImageDifusion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
        File file = new File("C:\\Users\\Fede\\Downloads\\18022013893.jpg");
        System.out.println(file.getName());
    }

    public String getExtension(String url) {
        int ind = url.lastIndexOf('.');
        return url.substring(ind + 1, url.length());
    }

    public String getName(String url) {
        int ind = url.lastIndexOf('.');
        return url.substring(0, ind);
    }

    public String getIdGroup() {
        return idGroup;
    }

    public File getImage(String url) {
        File file = new File(url);
        try {
            if (file.exists()) {
                return null;
            }
            if (file.isDirectory()) {
                file = new File(url + "\\" + name);
            }
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayInputStream imageByte = new ByteArrayInputStream(image);
            BufferedImage im = ImageIO.read(imageByte);
            ImageIO.write(im, this.extension, fos);
        } catch (IOException ex) {
            Logger.getLogger(ImageDifusion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public String getIdTransmitter() {
        return idTransmitter;
    }

    public void setIdTransmitter(String idTransmitter) {
        this.idTransmitter = idTransmitter;
    }
//    public static void main(String args[]) {
//        ImageDifusion id = new ImageDifusion("000");
//        id.setImage("C:\\c.jpg");
//        id.getImage("c1.jpg");
//    }
}

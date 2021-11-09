/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.icons;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import javafx.embed.swing.SwingFXUtils;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author thevpc
 */
public class FxIconUtils {

    /**
     * Changes all pixels of an old color into a new color, preserving the alpha
     * channel.
     */
    public static void changeColor(
            BufferedImage imgBuf,
            int oldRed, int oldGreen, int oldBlue,
            int newRed, int newGreen, int newBlue) {

        int RGB_MASK = 0x00ffffff;
        int ALPHA_MASK = 0xff000000;

        int oldRGB = oldRed << 16 | oldGreen << 8 | oldBlue;
        int toggleRGB = oldRGB ^ (newRed << 16 | newGreen << 8 | newBlue);

        int w = imgBuf.getWidth();
        int h = imgBuf.getHeight();

        int[] rgb = imgBuf.getRGB(0, 0, w, h, null, 0, w);
        for (int i = 0; i < rgb.length; i++) {
            if ((rgb[i] & RGB_MASK) == oldRGB) {
                rgb[i] ^= toggleRGB;
            }
        }
        imgBuf.setRGB(0, 0, w, h, rgb, 0, w);
    }

    public static boolean isSVG(URL u) {
        return u != null && u.toString().toLowerCase().endsWith(".svg");
    }

    public static ImageIcon loadFixedScaleImageIconSafe(URL u, int width, int height) {
        try {
            return loadFixedScaleImageIcon(u, width, height);
        } catch (Exception ex) {
            System.err.println("unable to load image " + u);
            ex.printStackTrace();
        }
        return null;
    }

    public static ImageIcon loadFactorScaleImageIconSafe(URL u, float width, float height) {
        try {
            return loadFactorScaleImageIcon(u, width, height);
        } catch (Exception ex) {
            System.err.println("unable to load image " + u);
            ex.printStackTrace();
        }
        return null;
    }

    public static ImageIcon loadFixedScaleImageIcon(URL u, int width, int height) {
        if (isSVG(u)) {
            return new ImageIcon(FxSVGSalamander.getFixedSizeSvg(u, width, height));
        } else {
            return new ImageIcon(getFixedSizeImage(Toolkit.getDefaultToolkit().getImage(u), width, height));
        }
    }

    public static ImageIcon loadFactorScaleImageIcon(URL u, float width, float height) {
        if (isSVG(u)) {
            return new ImageIcon(FxSVGSalamander.getFactorScaledSVG(u, width, height));
        } else {
            return new ImageIcon(getFactorScaledImage(Toolkit.getDefaultToolkit().getImage(u), width, height));
        }
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Invalid");
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static javafx.scene.image.Image toFxImage(Image image) {
        if (image == null) {
            return null;
        }
        return SwingFXUtils.toFXImage(toBufferedImage(image), null);
    }

    public static javafx.scene.image.Image toFxImage(ImageIcon icon) {
        if (icon == null) {
            return null;
        }
        return SwingFXUtils.toFXImage(toBufferedImage(icon.getImage()), null);
    }

    public static Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge
                    = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }

    public static Image getFactorScaledImage(Image srcImg, float w, float h) {
        if (w <= 0 && h <= 0) {
            return srcImg;
        }
        if (w <= 0 && h > 0) {
            w = h;
        } else if (h <= 0 && w > 0) {
            h = w;
        }
        int width = srcImg.getWidth(null);
        int height = srcImg.getHeight(null);
        int width2 = (int) (width * w);
        int height2 = (int) (height * h);
        BufferedImage resizedImg = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width2, height2, null);
        g2.dispose();

        return resizedImg;
    }

    public static Image getFixedSizeImage(Image srcImg, int w, int h) {
        if (w <= 0 && h <= 0) {
            return srcImg;
        }
        if (w >= 0 && h < 0) {
            h = w;
        } else if (h >= 0 && w < 0) {
            w = h;
        }
        int width = srcImg.getWidth(null);
        int height = srcImg.getHeight(null);
        if (w <= 0) {
            w = width;
        }
        if (h <= 0) {
            h = height;
        }
        if (width == w && height == h) {
            return srcImg;
        }
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

}

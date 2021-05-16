/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.icons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.Objects;
import javafx.embed.swing.SwingFXUtils;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.Application;
import net.thevpc.echo.iconset.IconTransform;

/**
 *
 * @author vpc
 */
public class FxAppColorIconTransform implements IconTransform {

    private Color from;
    private Color to;
    private Application app;

    public FxAppColorIconTransform(Color from, Color to,Application app) {
        this.from = from;
        this.to = to;
        this.app = app;
    }

    @Override
    public AppImage transformIcon(AppImage image) {
        //
        BufferedImage bi = SwingFXUtils.fromFXImage(FxAppImage.imageOf(image), null);
        BufferedImage bi2 = toBufferedImage(transformIcon(bi));
        return new net.thevpc.echo.impl.components.Image(
                FxAppImage.of(SwingFXUtils.toFXImage(bi2, null)),
                app
        );
    }

    public Image transformIcon(Image image) {
        if (from == null || to == null) {
            return image;
        }
        BufferedImage bimage = toBufferedImage(image);
        FxIconUtils.changeColor(bimage, from.getRed(), from.getGreen(), from.getBlue(), to.getRed(), to.getGreen(), to.getBlue());
//        ColorModel cm = bimage.getColorModel();
//        return new BufferedImage(createColorModel(color.getRGB()), bimage.getRaster(), false, null);

//        BufferedImage tintedImage = new BufferedImage(
//                bimage.getWidth(),
//                bimage.getHeight(),
//                BufferedImage.TRANSLUCENT);
//
//        Graphics graphics = tintedImage.createGraphics();
//        graphics.setXORMode(from);
//        graphics.drawImage(bimage, 0, 0, null); // NOT 'tintedImage'
//        graphics.dispose();
        return bimage;
    }

    private static ColorModel createColorModel(int n) {
        // Create a simple color model with all values mapping to
        // a single shade of gray
        // nb. this could be improved by reusing the byte arrays

        byte[] r = new byte[16];
        byte[] g = new byte[16];
        byte[] b = new byte[16];
        for (int i = 0; i < r.length; i++) {
            r[i] = (byte) n;
            g[i] = (byte) n;
            b[i] = (byte) n;
        }
        return new IndexColorModel(4, 16, r, g, b);
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.from);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FxAppColorIconTransform other = (FxAppColorIconTransform) obj;
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        return true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.icons;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.Objects;
import javax.swing.ImageIcon;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.Application;
import net.thevpc.echo.iconset.IconTransform;

/**
 *
 * @author vpc
 */
public class SwingColorIconTransform implements IconTransform {

    private Color from;
    private Color to;
    private Application app;

    public SwingColorIconTransform(Color from, Color to, Application app) {
        this.from = from;
        this.to = to;
        this.app = app;
    }

    @Override
    public AppImage transformIcon(AppImage image) {
        return new net.thevpc.echo.impl.components.Image(
                new SwingAppImage(transformIcon(((SwingAppImage) image).getImage())),app
        );
    }

    public Image transformIcon(Image image) {
        if (from == null || to == null) {
            return image;
        }
        BufferedImage bimage = IconUtils.toBufferedImage(image);
        IconUtils.changeColor(bimage, from.getRed(), from.getGreen(), from.getBlue(), to.getRed(), to.getGreen(), to.getBlue());
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
        final SwingColorIconTransform other = (SwingColorIconTransform) obj;
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        return true;
    }

}

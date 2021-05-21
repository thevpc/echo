/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.icons;

import java.awt.Image;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.spi.peers.AppImagePeer;

/**
 *
 * @author vpc
 */
public class SwingAppImage implements AppImagePeer {

    public static SwingAppImage of(AppImage a) {
        if (a instanceof SwingAppImage) {
            return ((SwingAppImage) a);
        }
        return null;
    }

    public static ImageIcon imageIconOf(AppImage a) {
        Icon b = iconOf(a);
        if(b==null){
            return null;
        }
        if(b instanceof ImageIcon){
            return ((ImageIcon)b);
        }
        return new ImageIcon(IconUtils.iconToImage(b));
    }
    
    public static Icon iconOf(AppImage a) {
        if (a instanceof SwingAppImage) {
            return ((SwingAppImage) a).getIcon();
        }
        if (a instanceof net.thevpc.echo.Image) {
            AppImagePeer p = ((net.thevpc.echo.Image) a).peer();
            if (p instanceof SwingAppImage) {
                return ((SwingAppImage) p).getIcon();
            }
        }
        return null;
    }

    private ImageIcon imageIcon;
    private SwingAppImage base;
    private URL baseURL;
    private boolean vector;

    public SwingAppImage(java.awt.Image any) {
        this(new ImageIcon(any),null,null,false);
    }

    public SwingAppImage(URL baseURL) {
        this(
                IconUtils.loadFactorScaleImageIconSafe(baseURL, 1f, 1f),
                null,
                baseURL,
                baseURL.toString().toLowerCase().endsWith(".svg")
        );
    }

    private SwingAppImage(ImageIcon imageIcon,SwingAppImage base,URL baseURL,boolean vector) {
        this.imageIcon = imageIcon;
        this.base=base;
        this.baseURL=baseURL;
        this.vector=vector;
    }

    @Override
    public boolean isVector() {
        return vector;
    }

    @Override
    public AppImagePeer scaleTo(double width, double height) {
        if(baseURL!=null) {
            return new SwingAppImage(
                    IconUtils.loadFixedScaleImageIconSafe(baseURL, (int) width, (int) height),
                    base==null?null:base.base,
                    baseURL,vector
            );
        }else if(base!=null){
            return new SwingAppImage(
                    new ImageIcon(IconUtils.getFixedSizeImage(base.imageIcon.getImage(), (int) width, (int) height)),
                    base,
                    baseURL,vector
            );
        }else{
            return new SwingAppImage(
                    new ImageIcon(IconUtils.getFixedSizeImage(imageIcon.getImage(), (int) width, (int) height)),
                    this,
                    baseURL,vector
            );
        }
    }

    @Override
    public AppImagePeer scaleBase(float widthFactor, float heightFactor) {
        if(baseURL!=null) {
            return new SwingAppImage(
                    IconUtils.loadFactorScaleImageIcon(baseURL, widthFactor, heightFactor),
                    base==null?null:base.base,
                    baseURL,vector
            );
        }else if(base!=null){
            return new SwingAppImage(
                    new ImageIcon(IconUtils.getFactorScaledImage(base.imageIcon.getImage(), widthFactor, heightFactor)),
                    base,
                    baseURL,vector
            );
        }else{
            return new SwingAppImage(
                    new ImageIcon(IconUtils.getFactorScaledImage(imageIcon.getImage(), widthFactor, heightFactor)),
                    this,
                    baseURL,vector
            );
        }
    }

    @Override
    public AppImagePeer scale(float widthFactor, float heightFactor) {
        if(baseURL!=null) {
            return new SwingAppImage(
                    new ImageIcon(IconUtils.getFactorScaledImage(imageIcon.getImage(), widthFactor, heightFactor)),
                    base==null?null:base.base,
                    baseURL,vector
            );
        }else if(base!=null){
            return new SwingAppImage(
                    new ImageIcon(IconUtils.getFactorScaledImage(imageIcon.getImage(), widthFactor, heightFactor)),
                    base,
                    baseURL,vector
            );
        }else{
            return new SwingAppImage(
                    new ImageIcon(IconUtils.getFactorScaledImage(imageIcon.getImage(), widthFactor, heightFactor)),
                    this,
                    baseURL,vector
            );
        }
    }

    @Override
    public Object toolkitImage() {
        return imageIcon;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public ImageIcon getIcon() {
        return imageIcon;
    }

    public Image getImage() {
        return imageIcon.getImage();
    }

    @Override
    public double getHeight() {
        return imageIcon.getIconHeight();
    }

    @Override
    public double getWidth() {
        return imageIcon.getIconWidth();
    }


}

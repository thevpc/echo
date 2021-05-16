/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.icons;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.peers.AppImagePeer;

import java.net.URL;

/**
 *
 * @author vpc
 */
public class FxAppImage implements AppImagePeer {

    public static ImageView imageViewOf(AppImage a) {
        Image i = imageOf(a);
        return i==null?null:new javafx.scene.image.ImageView(i);
    }
    
    public static Image imageOf(AppImage a) {
        FxAppImage i = of(a);
        return i == null ? null : i.getFxImage();
    }

    public static FxAppImage of(AppImage a) {
        if (a instanceof FxAppImage) {
            return (FxAppImage) a;
        }
        if (a instanceof net.thevpc.echo.impl.components.Image) {
            AppImagePeer p = ((net.thevpc.echo.impl.components.Image) a).peer();
            if (p instanceof FxAppImage) {
                return ((FxAppImage) p);
            }
        }
        return null;
    }

    public static FxAppImage of(Image a) {
        return new FxAppImage(a);
    }

    private Image fxImage;
    private FxAppImage base;
    private URL baseURL;
    private boolean vector;

    public FxAppImage(URL baseURL) {
        this(
                FxIconUtils.toFxImage(FxIconUtils.loadFactorScaleImageIconSafe(baseURL, 1f, 1f)),
                null,
                baseURL,
                baseURL.toString().toLowerCase().endsWith(".svg")
        );
    }

    private FxAppImage(Image fxImage,FxAppImage base,URL baseURL,boolean vector) {
        this.fxImage = fxImage;
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
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.loadFixedScaleImageIconSafe(baseURL, (int) width, (int) height)),
                    base==null?null:base.base,
                    baseURL,vector
            );
        }else if(base!=null){
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.getFixedSizeImage(SwingFXUtils.fromFXImage(base.fxImage,null), (int) width, (int) height)),
                    base,
                    baseURL,vector
            );
        }else{
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.getFixedSizeImage(SwingFXUtils.fromFXImage(fxImage,null), (int) width, (int) height)),
                    this,
                    baseURL,vector
            );
        }
    }


    @Override
    public AppImagePeer scaleBase(float widthFactor, float heightFactor) {
        if(baseURL!=null) {
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.loadFactorScaleImageIcon(baseURL, widthFactor, heightFactor)),
                    base==null?null:base.base,
                    baseURL,vector
            );
        }else if(base!=null){
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.getFactorScaledImage(SwingFXUtils.fromFXImage(base.fxImage,null), widthFactor, heightFactor)),
                    base,
                    baseURL,vector
            );
        }else{
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.getFactorScaledImage(SwingFXUtils.fromFXImage(fxImage,null), widthFactor, heightFactor)),
                    this,
                    baseURL,vector
            );
        }
    }

    @Override
    public AppImagePeer scale(float widthFactor, float heightFactor) {
        if(baseURL!=null) {
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.getFactorScaledImage(SwingFXUtils.fromFXImage(fxImage,null), widthFactor, heightFactor)),
                    base==null?null:base.base,
                    baseURL,vector
            );
        }else if(base!=null){
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.getFactorScaledImage(SwingFXUtils.fromFXImage(base.fxImage,null), widthFactor, heightFactor)),
                    base,
                    baseURL,vector
            );
        }else{
            return new FxAppImage(
                    FxIconUtils.toFxImage(FxIconUtils.getFactorScaledImage(SwingFXUtils.fromFXImage(fxImage,null), widthFactor, heightFactor)),
                    this,
                    baseURL,vector
            );
        }
    }

    @Override
    public Object toolkitImage() {
        return fxImage;
    }

    public FxAppImage(Image fxImage) {
        this.fxImage = fxImage;
    }

    @Override
    public double getHeight() {
        return fxImage.getHeight();
    }

    @Override
    public double getWidth() {
        return fxImage.getWidth();
    }

    public Image getFxImage() {
        return fxImage;
    }

}

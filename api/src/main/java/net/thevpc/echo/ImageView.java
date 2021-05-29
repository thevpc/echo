/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponentEvent;
import net.thevpc.echo.api.components.AppEventType;
import net.thevpc.echo.iconset.WritableImage;

import java.io.File;
import java.net.URL;

/**
 * @author vpc
 */
public class ImageView extends Label {
    private WritableFloat zoomFactor = Props.of("zoomFactor").floatOf(1);
    private WritableImage image;
    PropertyListener imageZoomUpdater = new PropertyListener() {
        @Override
        public void propertyUpdated(PropertyEvent e) {
            ImageView.this.app().runUI(() -> {
                AppImage newImage = image.get();
                if (newImage != null) {
                    AppImage base = newImage.scaleBase(1, 1);
                    double bw = base.getWidth();
                    double cw = newImage.getWidth();
                    float widthFactor = (float) (cw / bw);
                    //enableZoom().get() ? zoomFactor.get() : 1
                    ImageView.this.icon().set(newImage.scaleBase(widthFactor, widthFactor));
                    zoomFactor.set(widthFactor);
                } else {
                    zoomFactor.set(1);
                    ImageView.this.icon().set((AppImage) null);
                }
            });
        }
    };
    private WritableBoolean enableZoom = Props.of("enableZoom").booleanOf(false);

    public ImageView(Application app) {
        super(Str.empty(), app);
        image = new WritableImage("image", app,this);
        events().add((AppComponentEvent event) -> {
            if (event.isControlDown()) {
                if (event.isAltDown()) {
                    unzoom();
                } else {
                    if (event.wheelRotation() < 0) {
                        zoomIn();
                    } else {
                        zoomOut();
                    }
                }
            }
        }, AppEventType.MOUSE_WHEEL_MOVED);
        image().onChange(imageZoomUpdater);
        zoomFactor().onChange(imageZoomUpdater);
        enableZoom().onChange(imageZoomUpdater);
    }


    public static boolean isImage(String name) {
        String suffix = getFileExtension(name).toLowerCase();
        return suffix.equals("png") || suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("svg");
    }

    public static URL asURL(String url) {
        try {
            return new URL(url);
        } catch (Exception ex) {
        }
        //this is a file?
        File file1 = null;
        try {
            file1 = new File(url);
            return file1.toURI().toURL();
        } catch (Exception ex) {
        }
        return null;
    }

    public static String getFileExtension(String name) {
        name = name.replace('\\', '/');
        int x = name.lastIndexOf('/');
        if (x >= 0) {
            name = name.substring(x + 1);
        }
        x = name.lastIndexOf('.');
        String suffix = "";
        if (x >= 0) {
            suffix = name.substring(x + 1);
        }
        return suffix;
    }

    public WritableBoolean enableZoom() {
        return enableZoom;
    }

    public WritableFloat zoomFactor() {
        return zoomFactor;
    }

    public WritableImage image() {
        return image;
    }

    public void zoomIn() {
        zoom((float) (zoomFactor.get() * Math.pow(1.1, 1)));
    }

    public void zoomOut() {
        zoom((float) (zoomFactor.get() / Math.pow(1.1, 1)));
    }

    public void zoom(float zoom) {
        if (icon().get() == null) {
            return;
        }
        if (zoom != 1) {
            if (zoom < 1) {
                if (zoom * icon().get().getHeight() <= 1
                        || zoom * icon().get().getWidth() <= 1) {
                    return;
                }
            } else {
                if (zoom >= 100) {
                    return;
                }
            }
        }
        zoomFactor.set(zoom);
    }

    public void unzoom() {
        zoom(1);
    }
}

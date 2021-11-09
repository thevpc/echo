/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import net.thevpc.echo.Clipboard;
import net.thevpc.echo.PrinterService;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppUIPlaf;
import net.thevpc.echo.Application;
import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.jfx.icons.FxIconUtils;
import net.thevpc.echo.jfx.util.ColorUtils;
import net.thevpc.echo.spi.peers.AppColorPeer;
import net.thevpc.echo.spi.peers.AppFontPeer;
import net.thevpc.echo.spi.peers.AppImagePeer;
import net.thevpc.echo.iconset.IconTransform;
import net.thevpc.echo.impl.AbstractApplicationToolkit;
import net.thevpc.echo.jfx.icons.FxAppColorIconTransform;
import net.thevpc.echo.jfx.icons.FxAppImage;
import net.thevpc.echo.spi.peers.AppLabelPeer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author thevpc
 */
public class FxApplicationToolkit extends AbstractApplicationToolkit {

    private Map<String, ToggleGroup> buttonGroups = new HashMap<>();

    public FxApplicationToolkit() {
        super("javafx");
    }

    @Override
    public void initialize(Application app) {
        super.initialize(app);
        //addPeerFactory(AppLabelPeer.class, FxLabelPeer.class);
    }

    public ToggleGroup getButtonGroup(String name) {
        ToggleGroup p = buttonGroups.get(name);
        if (p == null) {
            p = new ToggleGroup();
            buttonGroups.put(name, p);
        }
        return p;
    }

    @Override
    public AppComponent createComponent(Object object) {
        if (object instanceof JComponent) {
            SwingNode sn = new SwingNode();
            sn.setContent((JComponent) object);
            object = sn;
        }
//        if (object instanceof Node
//                || object instanceof Window
//                || object instanceof MenuItem) {
//            Object obj = object;
//            AppComponentRenderer renderer = (c) -> new FxAppCustomControl(
//                    (AppUserControlModel) c.getTool(),
//                    c.getPath(), obj, application, null
//            );
//            UserControlModel tool = new UserControlModel(
//                    UUID.randomUUID().toString(),
//                    renderer, application
//            );
//            return createComponent(tool, null, Path.of(), null);
//        }
        if (object instanceof AppComponent) {
            return (AppComponent) object;
        }
        throw new IllegalArgumentException("not supported");
    }

    @Override
    public AppImagePeer createImagePeer(URL url) {
        return new FxAppImage(new Image(url.toString()));
    }

    @Override
    public AppUIPlaf[] loadAvailablePlafs() {
        return new AppUIPlaf[0];
    }

    @Override
    public void applyPlaf(String plaf) {

    }

    @Override
    public IconTransform createReplaceColorTransform(AppColor from, AppColor to) {
        return new FxAppColorIconTransform(
                (Color) from.peer().toolkitColor(),
                (Color) to.peer().toolkitColor(),
                app);
    }

    @Override
    public void runUI(Runnable run) {
        if (Platform.isFxApplicationThread()) {
            try {
                run.run();
            } catch (Exception e) {
                throw UncheckedException.wrap(e);
            }
        } else {
            Platform.runLater(run);
        }
    }

    @Override
    public void runWorker(Runnable run) {
        if (Platform.isFxApplicationThread()) {
            new Thread(run).start();
        } else {
            Platform.runLater(run);
        }
    }

    @Override
    public void runUILater(Runnable run) {
        Platform.runLater(run);
    }

    public void runUIAndWait(final Runnable run) {
        callUIAndWait(() -> {
            run.run();
            return null;
        });
    }

    public <T> T callUIAndWait(final Callable<T> run) {
        if (Platform.isFxApplicationThread()) {
            try {
                return run.call();
            } catch (Exception e) {
                throw UncheckedException.wrap(e);
            }
        } else {
            final Lock reentrantLock = new ReentrantLock();
            final Condition condition = reentrantLock.newCondition();
            class Ref {

                T t;
            }
            Ref r = new Ref();
            class ThrowableWrapper {

                Throwable t;
            }
            final ThrowableWrapper throwableWrapper = new ThrowableWrapper();
            reentrantLock.lock();
            try {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        reentrantLock.lock();
                        try {
                            r.t = run.call();
                        } catch (Throwable e) {
                            throwableWrapper.t = e;
                        } finally {
                            try {
                                condition.signal();
                            } finally {
                                reentrantLock.unlock();
                            }
                        }
                    }
                });
                try {
                    condition.await();
                } catch (InterruptedException ex) {
                    throw UncheckedException.wrap(ex);
                }
                if (throwableWrapper.t != null) {
                    throw UncheckedException.wrap(throwableWrapper.t);
                }
                return r.t;
            } finally {
                reentrantLock.unlock();
            }
        }
    }




//    @Override
//    public AppAlertPeer createAlertPeer(
//            AppAlert build
//    ) {
//        Alert bb = (Alert) build;
//        Alert.CustomContent customContent = bb.getCustomContent();
//        AppComponent content = build.getContent();
//        Node _mainComponent = FxPeer.nodeOf(content);
//        if (customContent != null) {
//            switch (customContent.getType()) {
//                case "TextField": {
//                    _mainComponent = new FxAppDialogPeer.InputTextFieldPanel(app,
//                            (String) customContent.getProperties().get("customContent"),
//                            (String) customContent.getProperties().get("initialValue")
//                    );
//                    break;
//                }
//                case "TextArea": {
//                    _mainComponent = new FxAppDialogPeer.InputTextAreaPanel(app,
//                            (String) customContent.getProperties().get("customContent"),
//                            (String) customContent.getProperties().get("initialValue")
//                    );
//                    break;
//                }
//            }
//        }
//        if (_mainComponent == null) {
//            _mainComponent = new javafx.scene.control.Label();
//        }
//        FxAppDialogPeer a = new FxAppDialogPeer(app, bb, bb.getTitle(), bb.getValueEvaluator(), bb.getTitleParameters());
//        if (bb.getPreferredSize() != null) {
//            a.setWidth(bb.getPreferredSize().getWidth());
//            a.setHeight(bb.getPreferredSize().getHeight());
//        }
//        a.build(_mainComponent, bb.getButtonIds().toArray(new String[0]), bb.getDefaultId(), bb.getAction());
//        return a;
//    }

    @Override
    public AppColorPeer createColorPeer(AppColor c) {
        return new FxColorPeer(c);
    }

    @Override
    public AppFontPeer createFontPeer(AppFont font) {
        return new FxFontPeer(font);
    }


    @Override
    public AppImagePeer createImagePeer(InputStream source) {
        return new FxAppImage(
                new Image(source)
        );
    }

    @Override
    public AppImagePeer createImagePeer(double width, double height, AppColor color) {
        BufferedImage image=new BufferedImage((int) width,(int) height,BufferedImage.TYPE_INT_ARGB);
        if(color!=null){
            Graphics2D g = image.createGraphics();
            g.setColor((Color) color.peer().toolkitColor());
            g.fillRect(0,0,(int)width,(int)height);
        }
        return new FxAppImage(FxIconUtils.toFxImage(image));
    }

    @Override
    public AppUIPlaf getPlaf(String id) {
        return null;
    }

    @Override
    public int parseColor(String colorText) {
        return ColorUtils.parseColor(colorText).getRGB();
    }

    @Override
    public Clipboard systemClipboard() {
        return new Clipboard() {
            @Override
            public String getString() {
                return null;
            }

            @Override
            public String getHtml() {
                return null;
            }

            @Override
            public void putString(String value) {

            }

            @Override
            public void putHtml(String value) {

            }
        };
    }

    @Override
    public PrinterService printerService() {
        throw new IllegalArgumentException("unsupported yet");
    }

    @Override
    public AppFont[] availablefonts() {
        throw new IllegalArgumentException("unsupported yet");
    }
    
}

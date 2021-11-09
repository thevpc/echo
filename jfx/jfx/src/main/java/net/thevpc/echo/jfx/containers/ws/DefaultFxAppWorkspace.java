///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.jfx.containers.ws;
//
//import java.util.HashMap;
//import java.util.Map;
//import javafx.scene.Node;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
////import jfxtras.scene.control.window.Window;
//import net.thevpc.common.props.PropertyEvent;
//import net.thevpc.common.props.PropertyListener;
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppFrame;
//import net.thevpc.echo.api.components.AppWorkspaceOptions;
//import net.thevpc.echo.api.peers.AppWorkspacePeer;
//import net.thevpc.echo.api.model.AppContainerModel;
//import net.thevpc.echo.api.model.AppWindowModel;
//import net.thevpc.echo.impl.components.Window;
////import net.thevpc.echo.impl.model.WindowModel;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.icons.FxAppImage;
//import net.thevpc.echo.jfx.raw.FxDockedPane;
//
///**
// *
// * @author thevpc
// */
//public class DefaultFxAppWorkspace
//        implements AppWorkspacePeer,FxPeer
//{
//
//    private AnchorPane desktopPane = new AnchorPane();
//    private Map<String, Window> internalFrames = new HashMap<>();
//
//    public DefaultFxAppWorkspace(AppContainerModel tool, Application app, AppWorkspaceOptions options, AppFrame win) {
//        super(tool, new FxDockedPane(), options, win, true, true);
//        if (desktopEnabled()) {
//            addWindow("Desktop",
//                    app().toolkit().createComponent(
//                            desktopPane
//                    ),
//                    WindowAnchor.CONTENT);
//        }
//    }
//
//    @Override
//    public void tileDesktop(boolean vertical) {
//        //
//    }
//
//    @Override
//    public void iconDesktop(boolean iconify) {
//        //
//    }
//
//    @Override
//    public void closeAllDesktop() {
//        //
//    }
//
//    protected static FxDockedPane.Anchor toAnchor2(WindowAnchor anchor1) {
//        FxDockedPane.Anchor a = FxDockedPane.Anchor.CENTER;
//        switch (anchor1) {
//            case BOTTOM: {
//                a = FxDockedPane.Anchor.BOTTOM;
//                break;
//            }
//            case DESKTOP: {
//                a = FxDockedPane.Anchor.CENTER;
//                break;
//            }
//            case CONTENT: {
//                a = FxDockedPane.Anchor.CENTER;
//                break;
//            }
//            case LEFT: {
//                a = FxDockedPane.Anchor.LEFT;
//                break;
//            }
//            case RIGHT: {
//                a = FxDockedPane.Anchor.RIGHT;
//                break;
//            }
//            case TOP: {
//                a = FxDockedPane.Anchor.TOP;
//                break;
//            }
//        }
//        return a;
//    }
//
//    @Override
//    public AppFrame addWindowImpl(String id, AppComponent component, WindowAnchor anchor) {
//        return app().toolkit().callUIAndWait(() -> {
//            FxDockedPane dd = (FxDockedPane) toolkitComponent();
//            net.thevpc.echo.impl.components.Window w = new net.thevpc.echo.impl.components.Window(id, anchor, component, app());
//            if (anchor == WindowAnchor.DESKTOP) {
//                jfxtras.scene.control.window.Window internalWindow = new jfxtras.scene.control.window.Window();
//                Node n = (Node) ((FxPeer) w.component().get()).toolkitComponent();
//                internalWindow.setContentPane(new BorderPane(n));
//                internalFrames.put(w.id(), internalWindow);
//                desktopPane.getChildren().add(internalWindow);
//            } else {
//                Node n = (Node) ((FxPeer) w.component().get()).toolkitComponent();
//                dd.add(w.id(), toAnchor2(w.anchor().get()), null, null, n, false);
//            }
//            w.closable().listeners().addInstall(() -> {
//                app().runUI(() -> {
//                    if (w.anchor().get() == WindowAnchor.DESKTOP) {
//                        Window ww = internalFrames.get(w.id());
//                        //ww.setClo
//                    } else {
//                        FxDockedPane dd2 = (FxDockedPane) toolkitComponent();
//                        dd2.setTabClosable(w.id(), w.closable().get());
//                    }
//                });
//            });
//            w.title().listeners().addInstall(() -> {
//                app().runUI(() -> {
//                    if (w.anchor().get() == WindowAnchor.DESKTOP) {
//
//                    } else {
//                        FxDockedPane dd2 = (FxDockedPane) toolkitComponent();
//                        dd2.setTabTitle(w.id(), w.title().get());
//                    }
//                });
//            });
//            w.icon().listeners().addInstall(() -> {
//                app().runUI(() -> {
//                    if (w.anchor().get() == WindowAnchor.DESKTOP) {
//                    } else {
//                        FxDockedPane dd2 = (FxDockedPane) toolkitComponent();
//                        dd2.setTabIcon(w.id(), FxAppImage.imageOf(w.icon().get()));
//                    }
//                });
//            });
//
//            w.anchor().onChange(new PropertyListener() {
//                @Override
//                public void propertyUpdated(PropertyEvent event) {
//                    if (w.anchor().get() == WindowAnchor.DESKTOP) {
//                        dd.remove(id);
//
//                        Window internalWindow = new Window();
//                        Node n = (Node) ((FxPeer) w.component()).toolkitComponent();
//                        internalWindow.setContentPane(new BorderPane(n));
//                        internalFrames.put(w.id(), internalWindow);
//                        desktopPane.getChildren().add(internalWindow);
//
//                    } else {
//                        Window internalWindow = internalFrames.remove(w.id());
//                        desktopPane.getChildren().remove(internalWindow);
//                        dd.add(id, toAnchor2(w.anchor().get()),
//                                w.title().get(),
//                                FxAppImage.imageOf(w.icon().get()),
//                                FxPeer.nodeOf(w.component().get()),
//                                w.closable().get()
//                        );
//                    }
//                }
//            });
//            return w;
//        });
//    }
//
//    @Override
//    protected void removeWindowImpl(String id, AppWindowModel atw) {
//        app().toolkit().runUI(() -> {
//            FxDockedPane dd = (FxDockedPane) toolkitComponent();
//            dd.remove(id);
//        });
//    }
//
//}

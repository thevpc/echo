//package net.thevpc.echo.jfx.containers.wins;
//
//import net.thevpc.common.props.PropertyEvent;
//import net.thevpc.common.props.PropertyListener;
//import net.thevpc.common.props.PropertyVeto;
//import net.thevpc.echo.*;
//
//import javax.swing.*;
//import java.awt.*;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//
//import javafx.beans.value.ChangeListener;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import net.thevpc.echo.api.AppImage;
//import net.thevpc.echo.api.components.*;
//import net.thevpc.echo.api.tools.AppContainerModel;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.icons.FxAppImage;
//
//public class FxFrameAppFrame extends AbstractAppFrame {
//
//    private boolean _in_windowClosing = false;
//    private boolean _in_windowClosed = false;
////    private ContainerAppTools tools;
//    BorderPane borderPane;
//    BorderPane menuPane;
//
//    public FxFrameAppFrame(AppContainerModel folder, Application application, AppComponentOptions options) {
//        this(folder, JavaFXRawApp.newStage(application), application, options);
//    }
//
//    public FxFrameAppFrame(AppContainerModel folder, Stage stage, Application application, AppComponentOptions options) {
//        super(folder, stage, application, options);
////        frame.setLayout(new BorderLayout());
////        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        menuPane = new BorderPane();
//        borderPane = new BorderPane();
//        menuPane.setCenter(borderPane);
//        application.toolkit().runUI(() -> stage.setScene(new Scene(menuPane,600,400)));
//        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                if ("lookAndFeel".equals(evt.getPropertyName())) {
//                    if (frame() != null) {
//                        //SwingUtilities.updateComponentTreeUI(frame);
//                    }
//                }
//            }
//        });
//        title().listeners().add(event -> application.toolkit().runUI(() -> frame().setTitle(event.getNewValue())));
//        icon().listeners().add(event -> application.toolkit().runUI(() -> frame().getIcons().add(FxAppImage.imageOf(((AppImage) event.getNewValue())))));
//
//        frame().iconifiedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(javafx.beans.value.ObservableValue<? extends Boolean> prop, Boolean oldValue, Boolean newValue) {
//                if (newValue) {
//                    state().add(AppWindowState.ICONIFIED);
//                } else {
//                    state().add(AppWindowState.ICONIFIED);
//                }
//            }
//        });
//        frame().maximizedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(javafx.beans.value.ObservableValue<? extends Boolean> prop, Boolean oldValue, Boolean newValue) {
//                if (newValue) {
//                    state().add(AppWindowState.MAXIMIZED_BOTH);
//                } else {
//                    state().remove(AppWindowState.MAXIMIZED_BOTH);
//                }
//            }
//        });
//        frame().focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(javafx.beans.value.ObservableValue<? extends Boolean> prop, Boolean oldValue, Boolean newValue) {
//                if (newValue) {
//                    state().add(AppWindowState.ACTIVATED);
//                } else {
//                    state().remove(AppWindowState.ACTIVATED);
//                }
//            }
//        });
//        frame().showingProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(javafx.beans.value.ObservableValue<? extends Boolean> prop, Boolean oldValue, Boolean newValue) {
//                if (newValue) {
//                    state().add(AppWindowState.OPENING);
//                } else {
//                    state().remove(AppWindowState.CLOSING);
//                }
//            }
//        });
//        state().listeners().add(event -> {
//            application.toolkit().runUI(() -> {
//                AppWindowStateSet aws = event.getNewValue();
//                if (aws == null) {
//                    aws = new AppWindowStateSet();
//                }
//                if (aws.is(AppWindowState.CLOSING)) {
//                    if (frame().showingProperty().get()) {
//                        frame().close();
//                    } else {
//                        state().add(AppWindowState.CLOSED);
//                    }
//                } else if (aws.is(AppWindowState.CLOSED)) {
//                    if (frame().showingProperty().get()) {
//                        frame().close();
//                    }
//                } else if (aws.is(AppWindowState.OPENED)) {
//                    if (!frame().showingProperty().get()) {
//                        frame().show();
//                    }
//                } else {
//                    if (aws.is(AppWindowState.ICONIFIED)) {
//                        frame().setIconified(true);
//                    } else if (aws.is(AppWindowState.DEICONIFIED)) {
//                        frame().setIconified(false);
//                    } else if (aws.is(AppWindowState.MAXIMIZED_BOTH)) {
//                        frame().setMaximized(true);
//                    } else if (aws.is(AppWindowState.MAXIMIZED_VERT)) {
//                        frame().setMaximized(true);
//                    } else if (aws.is(AppWindowState.MAXIMIZED_HORIZ)) {
//                        frame().setMaximized(true);
//                    } else if (aws.is(AppWindowState.NORMAL)) {
//                        frame().setMaximized(false);
//                    }
//                }
//            });
//        });
//        menuBar().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = event.getOldValue();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already BoundMenu");
//            }
//        });
//        menuBar().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                AppMenuBar v = event.getNewValue();
//                application.toolkit().runUI(() -> {
//                    menuPane.setTop(FxPeer.nodeOf(v));
//                });
//            }
//        });
//        toolBar().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = event.getOldValue();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already BoundM Toolbar");
//            }
//        });
//        toolBar().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                AppToolBarGroup v = event.getNewValue();
//                application.toolkit().runUI(() -> {
//                    System.out.println("set Fx Top "+ FxPeer.nodeOf(v));
//                    borderPane.setTop(FxPeer.nodeOf(v));
//                });
//            }
//        });
//        statusBar().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = event.getOldValue();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already BoundM Toolbar");
//            }
//        });
//        statusBar().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                AppStatusBarGroup v = event.getNewValue();
//                application.toolkit().runUI(() -> {
//                    System.out.println("set Fx Bottom "+ FxPeer.nodeOf(v));
//                    borderPane.setBottom(FxPeer.nodeOf(v));
//                });
//            }
//        });
//        workspace().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = event.getOldValue();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already BoundM Toolbar");
//            }
//        });
//        workspace().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                AppWorkspace v = event.getNewValue();
//                application.toolkit().runUI(() -> {
//                    borderPane.setCenter(FxPeer.nodeOf(v));
//                });
//            }
//        });
//        displayMode.listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                application.toolkit().runUI(() -> {
//                    applyDisplayMode();
//                });
//            }
//
//        });
//        application.toolkit().runUI(() -> {
//            applyDisplayMode();
//            frame().centerOnScreen();
//        });
//    }
//
//    private void applyDisplayMode() {
//        switch (displayMode().get()) {
//            case FULLSCREEN: {
//                frame().setFullScreen(true);
//                break;
//            }
//            default: {
//                frame().setFullScreen(false);
//                break;
//            }
//        }
//    }
//
//    @Override
//    public Object component() {
//        return guiElement();
//    }
//
//    @Override
//    public void centerOnDefaultMonitor() {
//        if (frame() != null && displayMode().get() != AppWindowDisplayMode.FULLSCREEN) {
//            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//            Rectangle sb = gd.getDefaultConfiguration().getBounds();
//            int swidth = gd.getDisplayMode().getWidth();
//            int sheight = gd.getDisplayMode().getHeight();
//            frame().setX(
//                    sb.x
//                    + swidth / 2 - frame().getWidth() / 2);
//            frame().setY(
//                    sb.y
//                    + sheight / 2 - frame().getHeight() / 2);
//        }
//    }
//
//    @Override
//    public void close() {
//        AppWindowStateSetValue _state = state();
//        if (!_state.is(AppWindowState.CLOSING)
//                && !_state.is(AppWindowState.CLOSED)) {
//            frame().hide();
//        }
//    }
//
//
//
//    Stage frame() {
//        return (Stage) guiElement();
//    }
//
//    protected void addImmediateImpl(int index, AppComponent childComponent) {
//        //
//    }
//
//}

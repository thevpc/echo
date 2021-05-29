package net.thevpc.echo.swing.peers;

import net.thevpc.common.msg.StringMessage;
import net.thevpc.echo.FrameDisplayMode;
import net.thevpc.echo.WindowState;
import net.thevpc.echo.WindowStateSet;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.api.components.AppMenuBar;
import net.thevpc.echo.api.components.AppToolBarGroup;
import net.thevpc.echo.spi.peers.AppFramePeer;
import net.thevpc.echo.swing.SwingApplicationUtils;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;

public class SwingFramePeer implements SwingPeer, AppFramePeer {

    private JFrame frame;
    private JPanel contentPanel;
    private AppFrame appComponent;
    private boolean _in_windowClosing = false;
    private boolean _in_windowClosed = false;

    @Override
    public void install(AppComponent component0) {
        this.appComponent = (AppFrame) component0;
        this.frame = new JFrame();
        contentPanel = new JPanel(new BorderLayout());
        if (appComponent.prefSize().get() != null) {
            this.frame.setPreferredSize(SwingHelpers.toAwtDimension(appComponent.prefSize().get()));
        }
        //title binding...
        this.appComponent.title().onChangeAndInit(() -> {
            appComponent.app().runUI(() -> {
                frame.setTitle(
                        appComponent.title().getOr(x -> x == null ? "" : x.value(appComponent.app().i18n(),appComponent.locale().get()))
                );
            });
        });
        this.appComponent.locale().onChangeAndInit(() -> {
            appComponent.app().runUI(() -> {
                frame.setTitle(
                        appComponent.title().getOr(x -> x == null ? "" : x.value(appComponent.app().i18n(),appComponent.locale().get()))
                );
            });
        });
        this.appComponent.icon().onChangeAndInit(()
                -> appComponent.app().runUI(() -> {
                    frame.setIconImage(
                            SwingHelpers.toAwtImage(this.appComponent.icon().get())
                    );
                }));
        appComponent.state().onChange(event -> {
            applyFrameState(appComponent.state().get());
        });
//        appComponent.menuBar().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = frame.getJMenuBar();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already BoundMenu");
//            }
//        });
        appComponent.menuBar().onChangeAndInit(() -> {
            appComponent.app().runUI(() -> {
                AppMenuBar v = appComponent.menuBar().get();
                if (v == null) {
                    frame.setJMenuBar(null);
                } else {
                    frame.setJMenuBar((JMenuBar) v.peer().toolkitComponent());
                }
                frame.invalidate();
                frame.revalidate();
                frame.repaint();
            });
        });
//        appComponent.toolBar().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = event.oldValue();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already Bound Toolbar");
//            }
//        });
        appComponent.toolBar().onChangeAndInit(() -> {
            appComponent.app().runUI(() -> {
                AppToolBarGroup v = appComponent.toolBar().get();
                setComponentByConstraint(v, BorderLayout.PAGE_START);
                frame.invalidate();
                frame.revalidate();
                frame.repaint();
            });
        });
        appComponent.statusBar().onChangeAndInit(() -> {
            appComponent.app().runUI(() -> {
                AppToolBarGroup v = appComponent.statusBar().get();
                setComponentByConstraint(v, BorderLayout.PAGE_END);
                frame.invalidate();
                frame.revalidate();
                frame.repaint();
            });
        });
        appComponent.content().onChangeAndInit(() -> {
            appComponent.app().runUI(() -> {
                AppComponent v = appComponent.content().get();
                setComponentByConstraint(v, BorderLayout.CENTER);
                frame.invalidate();
                frame.revalidate();
                frame.repaint();
            });
        });
//        appComponent.statusBar().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = event.oldValue();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already BoundM Toolbar");
//            }
//        });
//        appComponent.statusBar().onChange(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                AppToolBarGroup v = event.newValue();
//                setComponentByConstraint(v, BorderLayout.PAGE_END);
//            }
//        });
//        appComponent.content().vetos().add(new PropertyVeto() {
//            @Override
//            public void vetoableChange(PropertyEvent event) {
//                JMenuBar m = event.oldValue();
//                if (m == null) {
//                    return;
//                }
//                throw new IllegalArgumentException("Already BoundM Toolbar");
//            }
//        });
//        appComponent.content().onChange(event -> {
//            AppComponent v = event.newValue();
//            setComponentByConstraint(v, BorderLayout.CENTER);
//        });
        appComponent.displayMode().onChangeAndInit(() -> applyDisplayMode());
        appComponent.app().toolkit().runUI(() -> {

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            UIManager.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("lookAndFeel".equals(evt.getPropertyName())) {
                        if (frame != null) {
                            SwingUtilities.updateComponentTreeUI(frame);
                        }
                    }
                }
            });

            frame.addWindowStateListener(e -> {
                int s = e.getNewState();
                switch (s) {
                    case Frame.NORMAL: {
                        this.appComponent.state().add(WindowState.NORMAL);
                        break;
                    }
                    case Frame.ICONIFIED: {
                        this.appComponent.state().add(WindowState.ICONIFIED);
                        break;
                    }
                    case Frame.MAXIMIZED_HORIZ: {
                        this.appComponent.state().add(WindowState.MAXIMIZED_HORIZ);
                        break;
                    }
                    case Frame.MAXIMIZED_VERT: {
                        this.appComponent.state().add(WindowState.MAXIMIZED_VERT);
                        break;
                    }
                    case Frame.MAXIMIZED_BOTH: {
                        this.appComponent.state().add(WindowState.MAXIMIZED_BOTH);
                        break;
                    }
                }
            });
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                    appComponent.state().add(WindowState.OPENED);
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    if (!_in_windowClosing) {
                        try {
                            _in_windowClosing = true;
                            try {
                                appComponent.state().add(WindowState.CLOSING);
                            } catch (Exception ex) {
                                appComponent.app().logs().add(new StringMessage(Level.WARNING, "Closing Window canceled : " + ex.getMessage()));
                            }
                        } finally {
                            _in_windowClosing = false;
                        }
                    }
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    if (!_in_windowClosed) {
                        try {
                            _in_windowClosed = true;
                            try {
                                appComponent.state().add(WindowState.CLOSED);
                            } catch (Exception ex) {
                                appComponent.app().logs().add(new StringMessage(Level.WARNING, "Closing Window canceled : " + ex.getMessage()));
                            }
                        } finally {
                            _in_windowClosed = false;
                        }
                    }
                }

                @Override
                public void windowIconified(WindowEvent e) {
                    appComponent.state().add(WindowState.ICONIFIED);
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                    appComponent.state().add(WindowState.DEICONIFIED);
                }

                @Override
                public void windowActivated(WindowEvent e) {
                    appComponent.state().add(WindowState.ACTIVATED);
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                    appComponent.state().add(WindowState.DEACTIVATED);
                }
            }
            );
            applyDisplayMode();
            frame.setLocationRelativeTo(null);
            applyFrameState(appComponent.state().get());
        });
    }

    public void addChild(AppComponent other, int index) {
        appComponent.app().toolkit().runUI(() -> {
            String pn = other.path().get().last();
            if(pn==null){
                pn="";
            }
            Component a = (Component) other.peer().toolkitComponent();
            switch (pn) {
                case "content": {
                    contentPanel.add(a, BorderLayout.CENTER);
                    break;
                }
                case "toolBar": {
                    contentPanel.add(a, BorderLayout.NORTH);
                    break;
                }
                case "statusBar": {
                    contentPanel.add(a, BorderLayout.SOUTH);
                    break;
                }
                case "menuBar": {
                    frame.getContentPane().add(a, BorderLayout.NORTH);
                    break;
                }
            }
        });
    }

    public void removeChild(AppComponent other, int index) {
        appComponent.app().toolkit().runUI(() -> {
            frame.getContentPane().remove((Component) other.peer().toolkitComponent());
        });
    }

    @Override
    public Object toolkitComponent() {
        return frame;
    }

    private void setComponentByConstraint(AppComponent v, String constraint) {
        if (v == null) {
            BorderLayout layout = (BorderLayout) contentPanel.getLayout();
            Component old = layout.getLayoutComponent(constraint);
            if (old != null) {
                contentPanel.remove(old);
            }
        } else {
            contentPanel.add(SwingPeer.of(v).awtComponent(), constraint);
        }
    }

    private void applyFrameState(WindowStateSet aws) {
        if (aws == null) {
            aws = new WindowStateSet();
        }
        if (aws.is(WindowState.CLOSING)) {
            if (!_in_windowClosing) {
                try {
                    _in_windowClosing = true;
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                } finally {
                    _in_windowClosing = false;
                }
                if (frame.isVisible()) {
                    frame.setVisible(false);
                }
            }
        } else if (aws.is(WindowState.CLOSED)) {
            if (!_in_windowClosed) {
                try {
                    _in_windowClosed = true;
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
                } finally {
                    _in_windowClosed = false;
                }
                if (frame.isVisible()) {
                    frame.setVisible(false);
                }
            }
        } else if (aws.is(WindowState.OPENED)) {
            if (!frame.isVisible()) {
                SwingApplicationUtils.invokeLater(() -> {
                    frame.setMinimumSize(new Dimension(600, 400));
                    frame.pack();
                    frame.setVisible(true);
                });
            }
        } else {
            if (aws.is(WindowState.ICONIFIED) && (frame.getExtendedState() & Frame.ICONIFIED) == 0) {
                frame.setState(Frame.ICONIFIED);
            } else if (aws.is(WindowState.DEICONIFIED) && (frame.getExtendedState() & Frame.ICONIFIED) != 0) {
                frame.setState(Frame.NORMAL);
            } else if (aws.is(WindowState.MAXIMIZED_BOTH) && (frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == 0) {
                frame.setState(Frame.MAXIMIZED_BOTH);
            } else if (aws.is(WindowState.MAXIMIZED_VERT) && (frame.getExtendedState() & Frame.MAXIMIZED_VERT) == 0) {
                frame.setState(Frame.MAXIMIZED_VERT);
            } else if (aws.is(WindowState.MAXIMIZED_HORIZ) && (frame.getExtendedState() & Frame.MAXIMIZED_HORIZ) == 0) {
                frame.setState(Frame.MAXIMIZED_HORIZ);
            } else if (aws.is(WindowState.NORMAL)) {
                frame.setState(Frame.NORMAL);
            }
        }
    }

//    @Override
//    public void close() {
//        WindowStateSetValue _state = component.tool().state();
//        if (!_state.is(WindowState.CLOSING)
//                && !_state.is(WindowState.CLOSED)) {
//            frame.setVisible(false);
//            frame.dispose();
//        }
//    }
    private void applyDisplayMode() {
        appComponent.app().toolkit().runUI(() -> {

            switch (appComponent.displayMode().get()) {
                case FULLSCREEN: {
                    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

                    if (gd.isFullScreenSupported()) {
                        try {
                            gd.setFullScreenWindow(frame);
                        } finally {
                            System.err.println("setFullScreenWindow null");
                            //gd.setFullScreenWindow(null);
                        }
                    } else {
                        try {
                            gd.setFullScreenWindow(frame);
                        } finally {
//                        gd.setFullScreenWindow(null);
                        }
                    }
                    break;
                }
                default: {
                    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    gd.setFullScreenWindow(null);
                    break;
                }
            }
        });
    }

    @Override
    public void centerOnDefaultMonitor() {
        if (frame != null && appComponent.displayMode().get() != FrameDisplayMode.FULLSCREEN) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            Rectangle sb = gd.getDefaultConfiguration().getBounds();
            int swidth = gd.getDisplayMode().getWidth();
            int sheight = gd.getDisplayMode().getHeight();
            frame.setLocation(
                    sb.x
                    + swidth / 2 - frame.getSize().width / 2,
                    sb.x
                    + sheight / 2 - frame.getSize().height / 2);
        }
    }
}

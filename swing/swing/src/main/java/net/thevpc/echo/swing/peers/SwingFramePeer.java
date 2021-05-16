package net.thevpc.echo.swing.peers;

import net.thevpc.common.msg.StringMessage;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.PropertyVeto;
import net.thevpc.echo.AppWindowDisplayMode;
import net.thevpc.echo.AppWindowState;
import net.thevpc.echo.AppWindowStateSet;
import net.thevpc.echo.AppWorkspace;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.api.components.AppMenuBar;
import net.thevpc.echo.api.components.AppToolBarGroup;
import net.thevpc.echo.api.peers.AppFramePeer;
import net.thevpc.echo.swing.SwingApplicationUtils;
import net.thevpc.echo.swing.icons.SwingAppImage;

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
    private AppFrame component;
    private boolean _in_windowClosing = false;
    private boolean _in_windowClosed = false;

    @Override
    public void install(AppComponent component0) {
        this.component = (AppFrame) component0;
        this.frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        contentPanel = new JPanel(new BorderLayout());
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
        this.component.tool().title().listeners().add(event -> frame.setTitle(event.getNewValue()));
        this.component.tool().smallIcon().listeners().add(event -> frame.setIconImage(
                ((SwingAppImage) event.getNewValue()).getImage())
        );

        frame.addWindowStateListener(e -> {
            int s = e.getNewState();
            switch (s) {
                case Frame.NORMAL: {
                    this.component.tool().state().add(AppWindowState.NORMAL);
                    break;
                }
                case Frame.ICONIFIED: {
                    this.component.tool().state().add(AppWindowState.ICONIFIED);
                    break;
                }
                case Frame.MAXIMIZED_HORIZ: {
                    this.component.tool().state().add(AppWindowState.MAXIMIZED_HORIZ);
                    break;
                }
                case Frame.MAXIMIZED_VERT: {
                    this.component.tool().state().add(AppWindowState.MAXIMIZED_VERT);
                    break;
                }
                case Frame.MAXIMIZED_BOTH: {
                    this.component.tool().state().add(AppWindowState.MAXIMIZED_BOTH);
                    break;
                }
            }
        });
        frame.addWindowListener(new WindowListener() {
                                    @Override
                                    public void windowOpened(WindowEvent e) {
                                        component.tool().state().add(AppWindowState.OPENED);
                                    }

                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        if (!_in_windowClosing) {
                                            try {
                                                _in_windowClosing = true;
                                                try {
                                                    component.tool().state().add(AppWindowState.CLOSING);
                                                } catch (Exception ex) {
                                                    component.app().logs().add(new StringMessage(Level.WARNING, "Closing Window canceled : " + ex.getMessage()));
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
                                                    component.tool().state().add(AppWindowState.CLOSED);
                                                } catch (Exception ex) {
                                                    component.app().logs().add(new StringMessage(Level.WARNING, "Closing Window canceled : " + ex.getMessage()));
                                                }
                                            } finally {
                                                _in_windowClosed = false;
                                            }
                                        }
                                    }

                                    @Override
                                    public void windowIconified(WindowEvent e) {
                                        component.tool().state().add(AppWindowState.ICONIFIED);
                                    }

                                    @Override
                                    public void windowDeiconified(WindowEvent e) {
                                        component.tool().state().add(AppWindowState.DEICONIFIED);
                                    }

                                    @Override
                                    public void windowActivated(WindowEvent e) {
                                        component.tool().state().add(AppWindowState.ACTIVATED);
                                    }

                                    @Override
                                    public void windowDeactivated(WindowEvent e) {
                                        component.tool().state().add(AppWindowState.DEACTIVATED);
                                    }
                                }
        );
        component.tool().state().listeners().add(event -> {
            AppWindowStateSet aws = event.getNewValue();
            if (aws == null) {
                aws = new AppWindowStateSet();
            }
            if (aws.is(AppWindowState.CLOSING)) {
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
            } else if (aws.is(AppWindowState.CLOSED)) {
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
            } else if (aws.is(AppWindowState.OPENED)) {
                if (!frame.isVisible()) {
                    SwingApplicationUtils.invokeLater(() -> {
                        frame.setMinimumSize(new Dimension(600, 400));
                        frame.pack();
                        frame.setVisible(true);
                    });
                }
            } else {
                if (aws.is(AppWindowState.ICONIFIED) && (frame.getExtendedState() & Frame.ICONIFIED) == 0) {
                    frame.setState(Frame.ICONIFIED);
                } else if (aws.is(AppWindowState.DEICONIFIED) && (frame.getExtendedState() & Frame.ICONIFIED) != 0) {
                    frame.setState(Frame.NORMAL);
                } else if (aws.is(AppWindowState.MAXIMIZED_BOTH) && (frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == 0) {
                    frame.setState(Frame.MAXIMIZED_BOTH);
                } else if (aws.is(AppWindowState.MAXIMIZED_VERT) && (frame.getExtendedState() & Frame.MAXIMIZED_VERT) == 0) {
                    frame.setState(Frame.MAXIMIZED_VERT);
                } else if (aws.is(AppWindowState.MAXIMIZED_HORIZ) && (frame.getExtendedState() & Frame.MAXIMIZED_HORIZ) == 0) {
                    frame.setState(Frame.MAXIMIZED_HORIZ);
                } else if (aws.is(AppWindowState.NORMAL)) {
                    frame.setState(Frame.NORMAL);
                }
            }
        });
        component.menuBar().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = frame.getJMenuBar();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundMenu");
            }
        });
        component.menuBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppMenuBar v = event.getNewValue();
                JMenuBar m = frame.getJMenuBar();
                if (m != null) {
                    throw new IllegalArgumentException("");
                }
                if (v == null) {
                    frame.setJMenuBar(null);

                } else {
                    frame.setJMenuBar((JMenuBar) SwingPeer.jcompOf(v));
                }
            }
        });
        component.toolBar().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = event.getOldValue();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundM Toolbar");
            }
        });
        component.toolBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppToolBarGroup v = event.getNewValue();
                if (v == null) {
                    frame.add(null, BorderLayout.PAGE_START);
                } else {
                    frame.add(SwingPeer.of(v).awtComponent(), BorderLayout.PAGE_START);
                }
            }
        });
        component.statusBar().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = event.getOldValue();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundM Toolbar");
            }
        });
        component.statusBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppToolBarGroup v = event.getNewValue();
                if (v == null) {
                    frame.add(null, BorderLayout.PAGE_END);
                } else {
                    frame.add(SwingPeer.of(v).awtComponent(), BorderLayout.PAGE_END);
                }
            }
        });
        component.workspace().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = event.getOldValue();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundM Toolbar");
            }
        });
        component.workspace().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppWorkspace v = event.getNewValue();
                if (v == null) {
                    frame.add(null, BorderLayout.CENTER);
                } else {
                    frame.add(SwingPeer.of(v).awtComponent(), BorderLayout.CENTER);
                }
            }
        });
        component.tool().displayMode().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                applyDisplayMode();
            }

        });
        applyDisplayMode();
        frame.setLocationRelativeTo(null);
    }

    public void addChild(AppComponent other, int index) {
        component.app().toolkit().runUIAndWait(() -> {
            String pn = other.path().get().last();
            Component a = (Component) other.peer().toolkitComponent();
            switch (pn) {
                case "workspace": {
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
        component.app().toolkit().runUIAndWait(() -> {
            frame.getContentPane().remove((Component) other.peer().toolkitComponent());
        });
    }

    @Override
    public Object toolkitComponent() {
        return frame;
    }

//    @Override
//    public void close() {
//        AppWindowStateSetValue _state = component.tool().state();
//        if (!_state.is(AppWindowState.CLOSING)
//                && !_state.is(AppWindowState.CLOSED)) {
//            frame.setVisible(false);
//            frame.dispose();
//        }
//    }

    private void applyDisplayMode() {
        switch (component.tool().displayMode().get()) {
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
    }

    @Override
    public void centerOnDefaultMonitor() {
        if (frame != null && component.tool().displayMode().get() != AppWindowDisplayMode.FULLSCREEN) {
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

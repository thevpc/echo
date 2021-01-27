package net.thevpc.echo.swing.core.swing;

import net.thevpc.common.msg.StringMessage;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.PropertyVeto;
import net.thevpc.echo.AppWindowState;
import net.thevpc.echo.AppWindow;
import net.thevpc.echo.AppToolBar;
import net.thevpc.echo.Application;
import net.thevpc.echo.AppWorkspace;
import net.thevpc.echo.AppLayoutWindowFactory;
import net.thevpc.echo.AppMenuBar;
import net.thevpc.echo.AppStatusBar;
import net.thevpc.echo.AppWindowStateSet;
import net.thevpc.echo.ItemPath;
import net.thevpc.echo.swing.core.AbstractAppWindow;
import net.thevpc.echo.swing.core.ContainerAppTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;

public class JFrameAppWindow extends AbstractAppWindow {

    private JFrame frame;
    private boolean _in_windowClosing = false;
    private boolean _in_windowClosed = false;
    private ContainerAppTools tools;

    public JFrameAppWindow(ItemPath path, Application application) {
        this(path, new JFrame(), application);
    }

    public JFrameAppWindow(ItemPath path, JFrame frame0, Application application) {
        super(path, application);
        tools = new ContainerAppTools(application);
        this.frame = frame0;
        frame.setLayout(new BorderLayout());
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
        title().listeners().add(event -> frame.setTitle(event.getNewValue()));
        icon().listeners().add(event -> frame.setIconImage(((ImageIcon) event.getNewValue()).getImage()));

        frame.addWindowStateListener(e -> {
            int s = e.getNewState();
            switch (s) {
                case Frame.NORMAL: {
                    state().add(AppWindowState.NORMAL);
                    break;
                }
                case Frame.ICONIFIED: {
                    state().add(AppWindowState.ICONIFIED);
                    break;
                }
                case Frame.MAXIMIZED_HORIZ: {
                    state().add(AppWindowState.MAXIMIZED_HORIZ);
                    break;
                }
                case Frame.MAXIMIZED_VERT: {
                    state().add(AppWindowState.MAXIMIZED_VERT);
                    break;
                }
                case Frame.MAXIMIZED_BOTH: {
                    state().add(AppWindowState.MAXIMIZED_BOTH);
                    break;
                }
            }
        });
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                state().add(AppWindowState.OPENED);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (!_in_windowClosing) {
                    try {
                        _in_windowClosing = true;
                        try {
                            state().add(AppWindowState.CLOSING);
                        } catch (Exception ex) {
                            application.logs().add(new StringMessage(Level.WARNING, "Closing Window canceled : " + ex.getMessage()));
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
                            state().add(AppWindowState.CLOSED);
                        } catch (Exception ex) {
                            application.logs().add(new StringMessage(Level.WARNING, "Closing Window canceled : " + ex.getMessage()));
                        }
                    } finally {
                        _in_windowClosed = false;
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
                state().add(AppWindowState.ICONIFIED);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                state().add(AppWindowState.DEICONIFIED);
            }

            @Override
            public void windowActivated(WindowEvent e) {
                state().add(AppWindowState.ACTIVATED);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                state().add(AppWindowState.DEACTIVATED);
            }
        }
        );
        state().listeners().add(event -> {
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
                }
            } else if (aws.is(AppWindowState.CLOSED)) {
                if (!_in_windowClosed) {
                    try {
                        _in_windowClosed = true;
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
                    } finally {
                        _in_windowClosed = false;
                    }
                }
            } else if (aws.is(AppWindowState.OPENED)) {
                if (!frame.isVisible()) {
                    SwingHelper.invokeLater(() -> {
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
        menuBar().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = frame.getJMenuBar();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundMenu");
            }
        });
        menuBar().listeners().add(new PropertyListener() {
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
                    frame.setJMenuBar(((JMenuBarComponentSupplier) v).component());
                }
            }
        });
        toolBar().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = event.getOldValue();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundM Toolbar");
            }
        });
        toolBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppToolBar v = event.getNewValue();
                if (v == null) {
                    frame.add(null, BorderLayout.PAGE_START);
                } else {
                    frame.add(((JComponentSupplier) v).component(), BorderLayout.PAGE_START);
                }
            }
        });
        statusBar().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = event.getOldValue();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundM Toolbar");
            }
        });
        statusBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppStatusBar v = event.getNewValue();
                if (v == null) {
                    frame.add(null, BorderLayout.PAGE_END);
                } else {
                    frame.add(((JComponentSupplier) v).component(), BorderLayout.PAGE_END);
                }
            }
        });
        workspace().vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent event) {
                JMenuBar m = event.getOldValue();
                if (m == null) {
                    return;
                }
                throw new IllegalArgumentException("Already BoundM Toolbar");
            }
        });
        workspace().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppWorkspace v = event.getNewValue();
                if (v == null) {
                    frame.add(null, BorderLayout.CENTER);
                } else {
                    frame.add(((JComponentSupplier) v).component(), BorderLayout.CENTER);
                }
            }
        });
        displayMode.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                applyDisplayMode();
            }

        });
        applyDisplayMode();
    }

    private void applyDisplayMode() {
        switch (displayMode().get()) {
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

    public static AppLayoutWindowFactory factory() {
        return new AppLayoutWindowFactory() {
            @Override
            public AppWindow createWindow(String path, Application application) {
                return new JFrameAppWindow(ItemPath.of(path), application);
            }
        };
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public Object component() {
        return frame;
    }

}

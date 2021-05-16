/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing;

import net.thevpc.echo.AppUIPlaf;
import net.thevpc.echo.AppWorkspace;
import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.iconset.IconTransform;
import net.thevpc.echo.impl.AbstractApplicationToolkit;
import net.thevpc.echo.impl.components.CustomComponent;
import net.thevpc.echo.api.peers.AppColorPeer;
import net.thevpc.echo.api.peers.AppImagePeer;
import net.thevpc.echo.swing.containers.ws.SwingWorkspacePeer;
import net.thevpc.echo.swing.dialog.SwingAppAlertPeer;
import net.thevpc.echo.swing.dialog.SwingAppFileChooserPeer;
import net.thevpc.echo.swing.icons.SwingAppImage;
import net.thevpc.echo.swing.icons.SwingColorIconTransform;
import net.thevpc.echo.swing.peers.*;
import net.thevpc.swing.plaf.UIPlafManager;

import javax.swing.ButtonGroup;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Color;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author vpc
 */
public class SwingApplicationToolkit extends AbstractApplicationToolkit {

    private Map<String, ButtonGroup> buttonGroups = new HashMap<>();

    public SwingApplicationToolkit(SwingApplication application) {
        super(application);
//        setToolRenderer(AppToolAction.class, new SwingAppToolActionComponentRenderer());
        addPeerFactory(AppLabel.class, SwingLabelPeer.class);
        addPeerFactory(AppAlert.class, SwingAppAlertPeer.class);
        addPeerFactory(AppFileChooser.class, SwingAppFileChooserPeer.class);
        addPeerFactory(AppFrame.class, SwingFramePeer.class);
        addPeerFactory(AppButton.class, SwingButtonPeer.class);
        addPeerFactory(AppContextMenu.class, SwingContextMenuPeer.class);
        addPeerFactory(AppUserControl.class, SwingCustomPeer.class);
        addPeerFactory(AppMenuBar.class, SwingMenuBarPeer.class);
//        setPeerFactory(AppMenuButton.class, SwingMenuButtonPeer.class);
//        setPeerFactory(AppMenuLabel.class, SwingMenuLabelPeer.class);
        addPeerFactory(AppMenu.class, SwingMenuPeer.class);
        addPeerFactory(AppSeparator.class, SwingSeparatorPeer.class);
        addPeerFactory(AppSpacer.class, SwingSpacerPeer.class);
        addPeerFactory(AppToggle.class, SwingTogglePeer.class);
        addPeerFactory(AppToolBar.class, SwingToolBarPeer.class);
        addPeerFactory(AppToolBarGroup.class, SwingToolBarGroupPeer.class);
        addPeerFactory(AppContainer.class, SwingFolderPeer.class);
        addPeerFactory(AppWorkspace.class, SwingWorkspacePeer.class);
        addPeerFactory(AppPanel.class, SwingPanelPeer.class);
    }

    public ButtonGroup getButtonGroup(String name) {
        ButtonGroup p = buttonGroups.get(name);
        if (p == null) {
            p = new ButtonGroup();
            buttonGroups.put(name, p);
        }
        return p;
    }

    @Override
    public AppComponent createComponent(Object component) {
        if (component instanceof AppComponent) {
            return (AppComponent) component;
        }
        if (component instanceof Component) {
            return new CustomComponent(app,new SwingCustomPeer((Component) component));
        }
        throw new IllegalArgumentException("unsupported");
    }

    public AppUIPlaf[] loadAvailablePlafs() {
        return (UIPlafManager.INSTANCE.items())
                .stream().map(x -> new SwingUIPlaf(x))
                .toArray(AppUIPlaf[]::new);
    }

    @Override
    public void applyPlaf(String plaf) {
        if (plaf != null) {
            UIPlafManager.INSTANCE.apply(plaf);
        }
    }

    public IconTransform createReplaceColorTransform(Color from, Color to) {
        return new SwingColorIconTransform(from, to, app);
    }

    public void runUI(Runnable run) {
        if (SwingUtilities.isEventDispatchThread()) {
            run.run();
        } else {
            SwingUtilities.invokeLater(run);
        }
    }

    public void runWorker(Runnable run) {
        if (SwingUtilities.isEventDispatchThread()) {
            //may be should use executor!
            new Thread(run).start();
        } else {
            run.run();
        }
    }

    @Override
    public void runUILater(Runnable run) {
        SwingUtilities.invokeLater(run);
    }

    @Override
    public void runUIAndWait(Runnable run) {
        try {
            SwingUtilities.invokeAndWait(run);
        } catch (Exception ex) {
            throw UncheckedException.wrap(ex);
        }
    }

    @Override
    public <T> T callUIAndWait(Callable<T> run) {
        Object[] result = new Object[1];
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    result[0] = run.call();
                } catch (Exception ex) {
                    throw UncheckedException.wrap(ex);
                }
            });
        } catch (Exception ex) {
            throw UncheckedException.wrap(ex);
        }
        return (T) result[0];
    }

    @Override
    public AppImagePeer createImagePeer(URL url) {
        return new SwingAppImage(url);
    }

    @Override
    public AppColorPeer createColorPeer(int rgba, boolean hasAlpha) {
        return new SwingColorPeer(rgba, hasAlpha);
    }

}

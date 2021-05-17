/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing;

import net.thevpc.echo.AppUIPlaf;
import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.peers.*;
import net.thevpc.echo.iconset.IconTransform;
import net.thevpc.echo.impl.AbstractApplicationToolkit;
import net.thevpc.echo.impl.components.UserControl;
import net.thevpc.echo.swing.peers.SwingDesktopPeer;
import net.thevpc.echo.swing.peers.SwingDockPeer;
import net.thevpc.echo.swing.peers.SwingWindowPeer;
import net.thevpc.echo.swing.peers.SwingAppAlertPeer;
import net.thevpc.echo.swing.peers.SwingAppFileChooserPeer;
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
        addPeerFactory(AppLabelPeer.class, SwingLabelPeer.class);
        addPeerFactory(AppAlertPeer.class, SwingAppAlertPeer.class);
        addPeerFactory(AppFileChooserPeer.class, SwingAppFileChooserPeer.class);
        addPeerFactory(AppFramePeer.class, SwingFramePeer.class);
        addPeerFactory(AppButtonPeer.class, SwingButtonPeer.class);
        addPeerFactory(AppContextMenuPeer.class, SwingContextMenuPeer.class);
        addPeerFactory(AppUserControlPeer.class, SwingUserControlPeer.class);
        addPeerFactory(AppMenuBarPeer.class, SwingMenuBarPeer.class);
        addPeerFactory(AppMenuPeer.class, SwingMenuPeer.class);
        addPeerFactory(AppSeparatorPeer.class, SwingSeparatorPeer.class);
        addPeerFactory(AppSpacerPeer.class, SwingSpacerPeer.class);
        addPeerFactory(AppTogglePeer.class, SwingTogglePeer.class);
        addPeerFactory(AppCheckBoxPeer.class, SwingTogglePeer.class);
        addPeerFactory(AppRadioButtonPeer.class, SwingTogglePeer.class);
        addPeerFactory(AppToolBarPeer.class, SwingToolBarPeer.class);
        addPeerFactory(AppToolBarGroupPeer.class, SwingToolBarGroupPeer.class);
        addPeerFactory(AppPanelPeer.class, SwingPanelPeer.class);
        addPeerFactory(AppWindowPeer.class, SwingWindowPeer.class);
        addPeerFactory(AppDockPeer.class, SwingDockPeer.class);
        addPeerFactory(AppDesktopPeer.class, SwingDesktopPeer.class);
        addPeerFactory(AppTabsPeer.class, SwingTabsPeer.class);
        addPeerFactory(AppTextFieldPeer.class, SwingTextFieldPeer.class);
        addPeerFactory(AppTextAreaPeer.class, SwingTextAreaPeer.class);
        addPeerFactory(AppPasswordFieldPeer.class, SwingPasswordFieldPeer.class);
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
            UserControl userControl = new UserControl(app);
            userControl.setPeer(new SwingUserControlPeer((Component) component));
            return userControl;
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

    public IconTransform createReplaceColorTransform(AppColor from, AppColor to) {
        return new SwingColorIconTransform(
                (Color) from.peer().toolkitColor(),
                (Color) to.peer().toolkitColor()
                , app);
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

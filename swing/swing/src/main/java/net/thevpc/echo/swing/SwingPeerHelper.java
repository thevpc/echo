package net.thevpc.echo.swing;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContextMenu;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;

public class SwingPeerHelper {
    public static void installComponent(AppComponent appComponent, JComponent swingComponent) {
        appComponent.prefSize().listeners().addInstall(
                ()->swingComponent.setPreferredSize(SwingHelpers.toAwtDimension(appComponent.prefSize().get()))
        );
        appComponent.visible().listeners().addInstall(
                ()->swingComponent.setVisible(appComponent.visible().get())
        );
        appComponent.enabled().listeners().addInstall(
                ()->swingComponent.setEnabled(appComponent.enabled().get())
        );
        appComponent.tooltip().listeners().addInstall(
                ()->swingComponent.setToolTipText(appComponent.tooltip().getOr(x->x==null?null:x.value(appComponent.app().i18n())))
        );
        appComponent.contextMenu().listeners().addInstall(
                () -> {
                    AppContextMenu g = appComponent.contextMenu().get();
                    if (g == null) {
                        swingComponent.setComponentPopupMenu(null);
                    } else {
                        swingComponent.setComponentPopupMenu((JPopupMenu) g.peer().toolkitComponent());
                    }
                }
        );
    }
}

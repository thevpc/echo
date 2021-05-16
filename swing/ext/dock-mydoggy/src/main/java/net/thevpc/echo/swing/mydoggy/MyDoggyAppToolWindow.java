/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.swing.peers.SwingPeer;
import org.noos.xing.mydoggy.ToolWindow;
import org.noos.xing.mydoggy.ToolWindowAnchor;
import org.noos.xing.mydoggy.ToolWindowType;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author thevpc
 */
public class MyDoggyAppToolWindow  implements AppWindowPeer, SwingPeer{

    protected AppWindow win;
    protected MyDoggyToolWindowManager toolWindowManager;
    private ToolWindow toolWindow;
    private Application app;

    private static AppWindowAnchor toAppToolWindowAnchor(ToolWindowAnchor a) {
        switch (a) {
            case TOP:
                return AppWindowAnchor.TOP;
            case BOTTOM:
                return AppWindowAnchor.BOTTOM;
            case LEFT:
                return AppWindowAnchor.LEFT;
            case RIGHT:
                return AppWindowAnchor.RIGHT;
        }
        throw new IllegalArgumentException("unsupported");
    }
    private static ToolWindowAnchor toMyDoggyAnchor(AppWindowAnchor a) {
        switch (a) {
            case TOP:
                return ToolWindowAnchor.TOP;
            case BOTTOM:
                return ToolWindowAnchor.BOTTOM;
            case LEFT:
                return ToolWindowAnchor.LEFT;
            case RIGHT:
                return ToolWindowAnchor.RIGHT;
        }
        throw new IllegalArgumentException("unsupported");
    }

    public void install(AppComponent comp) {
        this.win = (AppWindow) comp;
        this.app = win.app();
        toolWindowManager = (MyDoggyToolWindowManager) win.parent().peer().toolkitComponent();
        Icon aim = win.tool().smallIcon().get()==null?null:
                (Icon) win.tool().smallIcon().get().peer().toolkitImage()
                ;

        this.toolWindow = toolWindowManager.registerToolWindow(
                win.tool().id(), win.tool().title().get()
                        .getValue(app.i18n()), aim,
                (Component) win.tool().component().get().peer().toolkitComponent()
                , toMyDoggyAnchor(win.tool().anchor().get()));
        for (ToolWindowType value : ToolWindowType.values()) {
            this.toolWindow.getTypeDescriptor(value).setIdVisibleOnTitleBar(false);
        }
        toolWindow.getRepresentativeAnchorDescriptor().setTitle(
                win.tool().title().get().getValue(app.i18n())
        );
        toolWindow.getRepresentativeAnchorDescriptor().setIcon(aim);
        win.tool().active().set(toolWindow.isActive());
//        win.tool().title().set(toolWindow.getTitle());
        Icon ic = toolWindow.getIcon();
//        this.icon.set(ic == null ? null : new SwingAppImage(ic));
        this.toolWindow.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case "active": {
                        win.tool().active().set((Boolean) evt.getNewValue());
                        break;
                    }
                }
            }
        });
        win.tool().active().listeners().add((PropertyEvent event) -> {
            toolWindow.setActive((Boolean) event.getNewValue());
        });
        win.tool().title().listeners().add((PropertyEvent event) -> {
            String newValue = (String) event.getNewValue();
            toolWindow.setTitle(newValue);
            toolWindow.getRepresentativeAnchorDescriptor().setTitle(newValue);
        });
        win.tool().smallIcon().listeners().add((PropertyEvent event) -> {
            AppImage i=event.getNewValue();
            Icon ii = getIcon(i);
            toolWindow.setIcon(ii);
            toolWindow.getRepresentativeAnchorDescriptor().setIcon(ii);
        });
        toolWindow.setAvailable(true);
    }

    private Icon getIcon(AppImage i) {
        if(i !=null){
            i = i.scaleTo(16,16);
        }
        Icon ii = i == null ? null : (Icon) i.peer().toolkitImage();
        return ii;
    }

    @Override
    public Object toolkitComponent() {
        return toolWindow;
    }

    @Override
    public void centerOnDesktop() {

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.echo.Bounds;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppWindowPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;
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

    private static Anchor toAppToolWindowAnchor(ToolWindowAnchor a) {
        switch (a) {
            case TOP:
                return Anchor.TOP;
            case BOTTOM:
                return Anchor.BOTTOM;
            case LEFT:
                return Anchor.LEFT;
            case RIGHT:
                return Anchor.RIGHT;
        }
        throw new IllegalArgumentException("unsupported");
    }
    private static ToolWindowAnchor toMyDoggyAnchor(Anchor a) {
        switch (a) {
            case TOP:
            case TOP_LEFT:
            case TOP_RIGHT:
                return ToolWindowAnchor.TOP;
            case BOTTOM:
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
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
        Icon aim = win.smallIcon().get()==null?null:
                SwingHelpers.toAwtIcon(win.smallIcon().get())
                ;

        this.toolWindow = toolWindowManager.registerToolWindow(
                win.id(), Applications.rawString(win.title(),comp), aim,
                (Component) win.component().get().peer().toolkitComponent()
                , toMyDoggyAnchor(win.anchor().get()));
        for (ToolWindowType value : ToolWindowType.values()) {
            this.toolWindow.getTypeDescriptor(value).setIdVisibleOnTitleBar(false);
        }
        toolWindow.getRepresentativeAnchorDescriptor().setTitle(
                Applications.rawString(win.title(),comp)
        );
        toolWindow.getRepresentativeAnchorDescriptor().setIcon(aim);
        win.active().set(toolWindow.isActive());
//        win.tool().title().set(toolWindow.getTitle());
        Icon ic = toolWindow.getIcon();
//        this.icon.set(ic == null ? null : new SwingAppImage(ic));
        this.toolWindow.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case "active": {
                        win.active().set((Boolean) evt.getNewValue());
                        break;
                    }
                }
            }
        });
        win.active().onChange((PropertyEvent event) -> {
            toolWindow.setActive((Boolean) event.newValue());
        });
        win.title().onChange((PropertyEvent event) -> {
            String newValue = (String) event.newValue();
            toolWindow.setTitle(newValue);
            toolWindow.getRepresentativeAnchorDescriptor().setTitle(newValue);
        });
        win.smallIcon().onChange((PropertyEvent event) -> {
            AppImage i=event.newValue();
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
        Icon ii = SwingHelpers.toAwtIcon(i);
        return ii;
    }

    @Override
    public Object toolkitComponent() {
        return toolWindow;
    }

    @Override
    public void resize(double x, double y, double w, double h) {

    }

//    @Override
//    public Bounds bounds() {
//        Rectangle r = toolWindow.getComponent().getBounds();
//        return new Bounds(r.getX(),r.getY(),r.getWidth(),r.getWidth());
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppWindowPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;
import net.thevpc.echo.swing.peers.SwingPeer;
import org.noos.xing.mydoggy.Content;
import org.noos.xing.mydoggy.ContentManager;
import org.noos.xing.mydoggy.Dockable;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author thevpc
 */
public class MyDoggyAppContentWindow implements AppWindowPeer, SwingPeer {

    protected AppWindow win;
    protected MyDoggyToolWindowManager toolWindowManager;
    private Content content;


    @Override
    public void install(AppComponent comp) {
        if (win == null) {
            win = (AppWindow) comp;
            toolWindowManager = (MyDoggyToolWindowManager) win.parent().peer().toolkitComponent();

            ContentManager contentManager = toolWindowManager.getContentManager();
            content = contentManager.addContent(win.id(),
                    Applications.rawString(win.title(),win), getIcon(win.icon().get()),
                    (Component) win.component().get().peer().toolkitComponent()
                    );

            content.setEnabled(true);
            content.getContentUI().setCloseable(false);
            content.getContentUI().setMaximizable(false);
            content.getContentUI().setMinimizable(false);
            content.getContentUI().setMinimizable(false);

            win.active().set(content.isSelected());
            win.enabled().set(content.isEnabled());
            content.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    switch (evt.getPropertyName()) {
                        case "visible":
                        case "selected": {
                            win.active().set(content.isVisible() && content.isSelected());
                            break;
                        }
                        case "enabled": {
                            win.enabled().set((Boolean) evt.getNewValue());
                            break;
                        }
                    }
                }
            });
            win.active().onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setSelected((Boolean) event.newValue());
                }
            });
            win.icon().onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setIcon(
                            getIcon(event.newValue())
                    );
                }
            });
            win.title().onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setTitle((String) event.newValue());
                }
            });
            win.locale().onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setTitle((String) event.newValue());
                }
            });
            win.component().onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setComponent((JComponent) event.newValue());
                }
            });
            win.closable().onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.getContentUI().setCloseable((Boolean) event.newValue());
                }
            });
        }
    }

    private Icon getIcon(AppImage i) {
        if(i !=null){
            i = i.scaleTo(16,16);
        }
        Icon ii = SwingHelpers.toAwtIcon(i);
        return ii;
    }



    public Dockable toolkitComponent() {
        return content;
    }
    @Override
    public void resize(double x, double y, double w, double h) {

    }

//    @Override
//    public Bounds bounds() {
//        Rectangle r = content.getComponent().getBounds();
//        return new Bounds(r.getX(),r.getY(),r.getWidth(),r.getWidth());
//    }
}

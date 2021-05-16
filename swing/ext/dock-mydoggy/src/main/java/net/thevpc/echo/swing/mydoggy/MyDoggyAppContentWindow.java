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
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.swing.icons.SwingAppImage;
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
            content = contentManager.addContent(win.tool().id(), win.tool().title().get().getValue(
                    win.app().i18n()
            ), getIcon(win.tool().smallIcon().get()),
                    (Component) win.tool().component().get().peer().toolkitComponent()
                    );

            content.setEnabled(true);
            content.getContentUI().setCloseable(false);
            content.getContentUI().setMaximizable(false);
            content.getContentUI().setMinimizable(false);
            content.getContentUI().setMinimizable(false);

            win.tool().active().set(content.isSelected());
            win.tool().enabled().set(content.isEnabled());
            content.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    switch (evt.getPropertyName()) {
                        case "visible":
                        case "selected": {
                            win.tool().active().set(content.isVisible() && content.isSelected());
                            break;
                        }
                        case "enabled": {
                            win.tool().enabled().set((Boolean) evt.getNewValue());
                            break;
                        }
                    }
                }
            });
            win.tool().active().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setSelected((Boolean) event.getNewValue());
                }
            });
            win.tool().smallIcon().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setIcon(
                            getIcon(event.getNewValue())
                    );
                }
            });
            win.tool().title().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setTitle((String) event.getNewValue());
                }
            });
            win.tool().component().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.setComponent((JComponent) event.getNewValue());
                }
            });
            win.tool().closable().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    content.getContentUI().setCloseable((Boolean) event.getNewValue());
                }
            });
        }
    }

    private Icon getIcon(AppImage i) {
        if(i !=null){
            i = i.scaleTo(16,16);
        }
        Icon ii = i == null ? null : (Icon) i.peer().toolkitImage();
        return ii;
    }
    @Override
    public void centerOnDesktop() {

    }


    public Dockable toolkitComponent() {
        return content;
    }
}

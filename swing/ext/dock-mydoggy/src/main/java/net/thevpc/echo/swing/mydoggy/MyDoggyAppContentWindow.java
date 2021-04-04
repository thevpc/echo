/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.JComponent;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.Props;
import net.thevpc.echo.AppContentWindow;
import net.thevpc.echo.AppWindowStateSet;
import net.thevpc.echo.Application;
import net.thevpc.echo.props.AppProps;
import net.thevpc.echo.props.AppWritableIcon;
import net.thevpc.echo.props.AppWritableString;
import org.noos.xing.mydoggy.Content;
import org.noos.xing.mydoggy.ContentManager;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author thevpc
 */
public class MyDoggyAppContentWindow implements AppContentWindow {

    private MyDoggyAppDockingWorkspace toolWindowManager;
    private WritableValue<Boolean> active = Props.of("activated").valueOf(Boolean.class, false);
    private WritableValue<Boolean> enabled = Props.of("enabled").valueOf(Boolean.class, false);
    private WritableValue<Boolean> closable = Props.of("closable").valueOf(Boolean.class, false);
    private AppWritableString title;
    private AppWritableIcon icon;
    private WritableValue<JComponent> component = Props.of("component").valueOf(JComponent.class, null);
    private WritableValue<AppWindowStateSet> state = Props.of("state").valueOf(AppWindowStateSet.class, new AppWindowStateSet());
    private String id;
    private Content content;
    private Application app;

    public MyDoggyAppContentWindow(MyDoggyAppDockingWorkspace toolWindowManager, String id, Component component,Application app) {
        this.toolWindowManager = toolWindowManager;
        this.id = id;
        this.component.set((JComponent) component);
        this.title = AppProps.of("title", app).strIdOf(id);
        this.icon = AppProps.of("icon", app).iconIdOf("$"+id+".icon"); //the dollar meens the the icon key is resolved from i18n

        ContentManager contentManager = toolWindowManager.getToolWindowManager().getContentManager();
        content = contentManager.addContent(id, title.get(), icon.get(), component);
        content.setEnabled(true);
        content.getContentUI().setCloseable(false);
        content.getContentUI().setMaximizable(false);
        content.getContentUI().setMinimizable(false);
        content.getContentUI().setMinimizable(false);

        active.set(content.isSelected());
        enabled.set(content.isEnabled());
        content.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case "visible": 
                    case "selected": 
                    {
                        active.set(content.isVisible() && content.isSelected());
                        break;
                    }
                    case "enabled": {
                        enabled.set((Boolean) evt.getNewValue());
                        break;
                    }
                }
            }
        });
        active.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                content.setSelected((Boolean) event.getNewValue());
            }
        });
        this.icon.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                content.setIcon((Icon) event.getNewValue());
            }
        });
        this.title.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                content.setTitle((String) event.getNewValue());
            }
        });
        this.component.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                content.setComponent((JComponent) event.getNewValue());
            }
        });
        this.closable.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                content.getContentUI().setCloseable((Boolean) event.getNewValue());
            }
        });
        toolWindowManager.contentWindows().put(id, this);
    }

    public WritableValue<Icon> icon() {
        return icon;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public WritableValue<Boolean> closable() {
        return closable;
    }

    @Override
    public WritableValue<String> title() {
        return title;
    }

    @Override
    public WritableValue<JComponent> component() {
        return component;
    }
    

    @Override
    public WritableValue<Boolean> active() {
        return active;
    }

    @Override
    public WritableValue<AppWindowStateSet> state() {
        return state;
    }

}

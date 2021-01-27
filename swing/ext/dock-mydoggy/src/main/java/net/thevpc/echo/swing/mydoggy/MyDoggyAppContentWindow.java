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
import net.thevpc.common.props.WritablePValue;
import net.thevpc.echo.AppContentWindow;
import net.thevpc.echo.AppWindowStateSet;
import org.noos.xing.mydoggy.Content;
import org.noos.xing.mydoggy.ContentManager;

/**
 *
 * @author thevpc
 */
public class MyDoggyAppContentWindow implements AppContentWindow {

    private MyDoggyAppDockingWorkspace toolWindowManager;
    private WritablePValue<Boolean> active = Props.of("activated").valueOf(Boolean.class, false);
    private WritablePValue<Boolean> enabled = Props.of("enabled").valueOf(Boolean.class, false);
    private WritablePValue<Boolean> closable = Props.of("closable").valueOf(Boolean.class, false);
    private WritablePValue<String> title = Props.of("title").valueOf(String.class, "");
    private WritablePValue<String> icon = Props.of("icon").valueOf(String.class, "");
    private WritablePValue<JComponent> component = Props.of("component").valueOf(JComponent.class, null);
    private WritablePValue<AppWindowStateSet> state = Props.of("state").valueOf(AppWindowStateSet.class, new AppWindowStateSet());
    private String id;
    private Content content;

    public MyDoggyAppContentWindow(MyDoggyAppDockingWorkspace toolWindowManager, String id, String title, Icon icon, Component component) {
        this.toolWindowManager = toolWindowManager;
        this.id = id;
        this.component.set((JComponent) component);

        ContentManager contentManager = toolWindowManager.getToolWindowManager().getContentManager();
        content = contentManager.addContent(id, title, null, component);
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
                content.setIcon(toolWindowManager.application().iconSet().icon((String) event.getNewValue()).get());
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

    public WritablePValue<String> icon() {
        return icon;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public WritablePValue<Boolean> closable() {
        return closable;
    }

    @Override
    public WritablePValue<String> title() {
        return title;
    }

    @Override
    public WritablePValue<JComponent> component() {
        return component;
    }
    

    @Override
    public WritablePValue<Boolean> active() {
        return active;
    }

    @Override
    public WritablePValue<AppWindowStateSet> state() {
        return state;
    }

}

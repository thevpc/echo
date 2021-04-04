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
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.Props;
import net.thevpc.echo.AppToolWindow;
import net.thevpc.echo.Application;
import net.thevpc.echo.props.AppProps;
import net.thevpc.echo.props.AppWritableIcon;
import net.thevpc.echo.props.AppWritableString;
import org.noos.xing.mydoggy.ToolWindow;
import org.noos.xing.mydoggy.ToolWindowAnchor;
import org.noos.xing.mydoggy.ToolWindowType;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author thevpc
 */
public class MyDoggyAppToolWindow implements AppToolWindow {

    private MyDoggyAppDockingWorkspace toolWindowManager;
    private WritableValue<Boolean> active = Props.of("activated").valueOf(Boolean.class, false);
    private AppWritableString title;
    private AppWritableIcon icon;
    private ToolWindow toolWindow;
    private String id;
    private Application app;

    public MyDoggyAppToolWindow(MyDoggyAppDockingWorkspace toolWindowManager, String id, Component component, ToolWindowAnchor anchor, Application app) {
        this.app = app;
        this.toolWindowManager = toolWindowManager;
        this.id = id;
        this.app = app;
        this.title = AppProps.of("title", app).strIdOf(id);
        this.icon = AppProps.of("icon", app).iconIdOf("$"+id + ".icon"); //the dollar meens the the icon key is resolved from i18n

        this.toolWindow = toolWindowManager.getToolWindowManager().registerToolWindow(id, title.get(), icon.get(), component, anchor);
        for (ToolWindowType value : ToolWindowType.values()) {
            this.toolWindow.getTypeDescriptor(value).setIdVisibleOnTitleBar(false);
        }
        toolWindow.getRepresentativeAnchorDescriptor().setTitle(title.get());
        toolWindow.getRepresentativeAnchorDescriptor().setIcon(icon.get());
        this.active.set(toolWindow.isActive());
        this.title.set(toolWindow.getTitle());
        this.icon.set(toolWindow.getIcon());
        this.toolWindow.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case "active": {
                        active.set((Boolean) evt.getNewValue());
                        break;
                    }
                }
            }
        });
        this.active.listeners().add((PropertyEvent event) -> {
            toolWindow.setActive((Boolean) event.getNewValue());
        });
        this.title.listeners().add((PropertyEvent event) -> {
            String newValue = (String) event.getNewValue();
            toolWindow.setTitle(newValue);
            toolWindow.getRepresentativeAnchorDescriptor().setTitle(newValue);
        });
        this.icon.listeners().add((PropertyEvent event) -> {
            Icon newIcon=(Icon) event.getNewValue();
            toolWindow.setIcon(newIcon);
            toolWindow.getRepresentativeAnchorDescriptor().setIcon(newIcon);
        });
        toolWindowManager.toolWindows().put(id, this);
        toolWindow.setAvailable(true);
    }

    @Override
    public AppWritableString title() {
        return title;
    }

    @Override
    public AppWritableIcon icon() {
        return icon;
    }

    @Override
    public String id() {
        return id;
    }

    public WritableValue<Boolean> active() {
        return active;
    }

}

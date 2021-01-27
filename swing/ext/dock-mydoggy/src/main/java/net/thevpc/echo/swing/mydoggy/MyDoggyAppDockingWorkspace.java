/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import net.thevpc.echo.*;
import net.thevpc.echo.*;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritablePMap;
import org.noos.xing.mydoggy.ToolWindowAnchor;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import net.thevpc.echo.swing.core.swing.JComponentSupplier;

/**
 *
 * @author thevpc
 */
public class MyDoggyAppDockingWorkspace implements AppDockingWorkspace, JComponentSupplier {

    private MyDoggyToolWindowManager toolWindowManager;
    private Application application;
    private AppWindow window;
    private WritablePMap<String, AppToolWindow> toolWindows = Props.of("toolWindows").mapOf(String.class, AppToolWindow.class);
    private WritablePMap<String, AppContentWindow> contentWindows = Props.of("contentWindows").mapOf(String.class, AppContentWindow.class);

    public static AppLayoutWorkspaceFactory factory() {
        return new AppLayoutWorkspaceFactory() {
            @Override
            public AppWorkspace createWorkspace(AppWindow window) {
                return new MyDoggyAppDockingWorkspace(window);
            }
        };
    }

    public MyDoggyAppDockingWorkspace(AppWindow window) {
        this(new MyDoggyToolWindowManager());
        this.window=window;
        this.application=window.application();
    }

    public MyDoggyAppDockingWorkspace(MyDoggyToolWindowManager toolWindowManager) {
        this.toolWindowManager = toolWindowManager;
    }

    @Override
    public Application application() {
        return application;
    }

    @Override
    public JComponent component() {
        return toolWindowManager;
    }

    public MyDoggyToolWindowManager getToolWindowManager() {
        return toolWindowManager;
    }

    @Override
    public WritablePMap<String, AppToolWindow> toolWindows() {
        return toolWindows;
    }

    @Override
    public WritablePMap<String, AppContentWindow> contentWindows() {
        return contentWindows;
    }

    @Override
    public AppContentWindow getContent(String id) {
        return contentWindows.get(id);
    }

    @Override
    public AppToolWindow getTool(String id) {
        return toolWindows.get(id);
    }

    @Override
    public AppContentWindow addContent(String id, String title, ImageIcon icon, JComponent component) {
        AppContentWindow w = contentWindows.get(id);
        if (w != null) {
            throw new IllegalArgumentException("Already Registered");
        }
        MyDoggyAppContentWindow c = new MyDoggyAppContentWindow(this, id, title, icon, component);
        contentWindows.put(id, c);
        return c;
    }

    @Override
    public AppToolWindow addTool(String id, String title, ImageIcon icon, JComponent component, AppToolWindowAnchor anchor) {
        ToolWindowAnchor jdanchor = ToolWindowAnchor.LEFT;
        switch (anchor) {
            case TOP: {
                jdanchor = ToolWindowAnchor.TOP;
                break;
            }
            case BOTTOM: {
                jdanchor = ToolWindowAnchor.BOTTOM;
                break;
            }
            case LEFT: {
                jdanchor = ToolWindowAnchor.LEFT;
                break;
            }
            case RIGHT: {
                jdanchor = ToolWindowAnchor.RIGHT;
                break;
            }
        }
        MyDoggyAppToolWindow t = new MyDoggyAppToolWindow(this, id, title, icon, component, jdanchor);
        toolWindows.put(id, t);
        return t;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.AppToolAction;
import net.thevpc.echo.AppToolActionBuilder;
import net.thevpc.echo.ItemPath;
import net.thevpc.echo.swing.core.tools.AppToolActionImpl;

/**
 *
 * @author vpc
 */
class DefaultAppToolActionBuilder implements AppToolActionBuilder {

    private String id;
    private Action al;
    private java.util.List<String> paths = new ArrayList<String>();
    private java.util.List<AppToolComponent<AppToolAction>> components = null;
    private AppToolAction tool = null;
    private AbstractAppToolsBase tools;

    public DefaultAppToolActionBuilder(AbstractAppToolsBase tools) {
        this.tools = tools;
    }

    public AppToolActionBuilder bind(ActionListener a) {
        this.al = (a instanceof Action) ? (Action) a : new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (a != null) {
                    a.actionPerformed(e);
                }
            }
        };
        return this;
    }

    public AppToolActionBuilder bind(Runnable a) {
        bind(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (a != null) {
                    a.run();
                }
            }
        });
        return this;
    }

    public AppToolActionBuilder id(String g) {
        this.id = g;
        return this;
    }

    public AppToolActionBuilder path(String... g) {
        this.paths.addAll(Arrays.asList(g));
        return this;
    }

    public AppToolActionBuilder build() {
        if (components != null) {
            throw new IllegalArgumentException("already build");
        }
        components = new ArrayList<>();
        String path = paths.get(0);
        AppToolComponent<AppToolAction> c = null;
        {
            ItemPath ipath = ItemPath.of(path);
            String _id = id;
            if (_id == null) {
                _id = ipath.isEmpty() ? "unknown" : ipath.name();
            }
            path = ipath.toString();
            AppToolAction action = new AppToolActionImpl(_id, null, tools.application, tools);
            action.title().setId(_id);
            action.smallIcon().setId("$" + _id + ".icon"); //the dollar meens the the icon key is resolved from i18n
            c = AppToolComponent.of(action, path);
            tools.addTool(c);
            components.add(c);
        }
        AppToolAction tool = c.tool();
        path = ItemPath.of(path).toString();
        tool.title().setId(path);
        tool.smallIcon().setId("$" + path + ".icon"); //the dollar meens the the icon key is resolved from i18n
        tool.action().set(al == null ? new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        } : al);
        for (int i = 1; i < paths.size(); i++) {
            components.add(c.copyTo(tools, paths.get(i)));
        }
        this.tool = tool;
        return this;
    }

    public AppToolAction tool() {
        if (components == null) {
            build();
        }
        return tool;
    }

    public List<AppToolComponent<AppToolAction>> components() {
        if (components == null) {
            build();
        }
        return components;
    }

    @Override
    public AppToolComponent<AppToolAction> component() {
        return components().get(0);
    }

}

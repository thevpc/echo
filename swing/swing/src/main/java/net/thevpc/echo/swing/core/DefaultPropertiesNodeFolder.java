/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.echo.AppPropertiesNode;
import net.thevpc.echo.AppPropertiesNodeFolder;

/**
 *
 * @author thevpc
 */
public class DefaultPropertiesNodeFolder extends AbstractPropertiesNode implements AppPropertiesNodeFolder {

    String name;
    Object object;

    private List<AppPropertiesNode> children = new ArrayList<AppPropertiesNode>();

    public DefaultPropertiesNodeFolder(String type, String name) {
        super(type);
        this.name = name;
    }

    public DefaultPropertiesNodeFolder(String type, String name, Object object) {
        super(type);
        this.name = name;
        this.object = object;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object object() {
        return object;
    }

    public void add(AppPropertiesNode child) {
        ((AbstractPropertiesNode) child).folder = this;
        ((AbstractPropertiesNode) child).tree = tree;
        children.add(child);
    }

    @Override
    public AppPropertiesNode[] children() {
        return children.toArray(new AppPropertiesNode[0]);
    }

    public DefaultPropertiesNodeFolder addFolder(String type, String name) {
        DefaultPropertiesNodeFolder child = new DefaultPropertiesNodeFolder(type, name);
        add(child);
        return child;
    }

}

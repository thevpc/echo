/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core;

import java.util.HashMap;
import java.util.Map;

import net.thevpc.echo.AppPropertiesNode;
import net.thevpc.echo.AppPropertiesNodeFolder;
import net.thevpc.echo.AppPropertiesTree;

/**
 *
 * @author thevpc
 */
public abstract class AbstractPropertiesNode implements AppPropertiesNode {

    private final String type;
    AppPropertiesNodeFolder folder;
    AppPropertiesTree tree;
    private Map<String, Object> userObjects;

    public AbstractPropertiesNode(String type) {
        this.type = type;
    }

    @Override
    public Object getUserObject(String n) {
        if (n != null && userObjects != null) {
            return userObjects.get(n);
        }
        return null;
    }

    @Override
    public void putUserObject(String n, Object value) {
        if (n != null) {
            if (value != null) {
                if (userObjects == null) {
                    userObjects = new HashMap<>();
                }
                userObjects.put(n, value);
            } else {
                if (userObjects != null) {
                    userObjects.remove(n);
                    if (userObjects.isEmpty()) {
                        userObjects = null;
                    }
                }
            }
        }
    }

    @Override
    public AppPropertiesTree tree() {
        return tree;
    }

    @Override
    public AppPropertiesNodeFolder parent() {
        return folder;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String toString() {
        return String.valueOf(name());
    }

}

/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 *
 * Copyright [2021] [thevpc] Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * <br> ====================================================================
 */



package net.thevpc.echo.impl;

import java.util.HashMap;
import java.util.Map;

import net.thevpc.echo.api.AppPropertiesNode;
import net.thevpc.echo.api.AppPropertiesNodeFolder;
import net.thevpc.echo.api.AppPropertiesTree;

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

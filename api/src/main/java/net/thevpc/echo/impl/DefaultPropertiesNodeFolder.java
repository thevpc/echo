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

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


package net.thevpc.echo;

import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.tools.AppComponentModel;

public class AppComponentRendererContext {
    private Application app;
    private AppComponent parent;
    private AppComponentModel tool;
    private Path path;
    private String name;
    private AppComponentOptions options;
    private Class<? extends AppComponent> componentType;

    public AppComponentRendererContext(Class<? extends AppComponent> componentType, AppComponent parent, AppComponentModel tool, Application app, Path path, String name, AppComponentOptions options) {
        this.parent = parent;
        this.tool = tool;
        this.app = app;
        this.path = path;
        this.name = name;
        this.options = options;
        this.componentType = componentType;
    }

    public ApplicationToolkit toolkit() {
        return app().toolkit();
    }

    public String name() {
        return name;
    }

    public AppComponentOptions options() {
        return options;
    }

    public Path path() {
        return path;
    }

    public AppComponent parent() {
        return parent;
    }

    public Class<? extends AppComponent> componentType() {
        return componentType;
    }

    public AppComponentModel tool() {
        return tool;
    }

    public Application app() {
        return app;
    }
}

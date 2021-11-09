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

import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.iconset.DefaultIconSet;
import net.thevpc.echo.iconset.IconSet;
import net.thevpc.echo.iconset.IconSetBuilder;
import net.thevpc.echo.iconset.IconTransform;

import java.awt.*;

/**
 *
 * @author thevpc
 */
public class DefaultIconSetSupplier implements IconSetBuilder {

    private Application app;
    private String name;
    private String path;
    private ClassLoader classLoader;
    private IconTransform transform;

    public DefaultIconSetSupplier(Application app) {
        this.app = app;
    }

    @Override
    public IconSetBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IconSetBuilder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public IconSetBuilder classLoader(ClassLoader loader) {
        this.classLoader = loader;
        return this;
    }

    @Override
    public IconSetBuilder transform(IconTransform transform) {
        this.transform = transform;
        return this;
    }

    @Override
    public IconSet build() {
        return new DefaultIconSet(name, path,
                classLoader == null ? Thread.currentThread().getContextClassLoader() : classLoader,
                transform, app);
    }

    public IconSetBuilder replaceColor(AppColor from, AppColor to) {
        transform(
                app.toolkit().createReplaceColorTransform(from, to)
        );
        return this;
    }

}

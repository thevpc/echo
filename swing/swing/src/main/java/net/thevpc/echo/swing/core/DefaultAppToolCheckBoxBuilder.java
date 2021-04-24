/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.AppToolCheckBox;
import net.thevpc.echo.AppToolCheckBoxBuilder;
import net.thevpc.echo.ItemPath;
import net.thevpc.echo.swing.core.tools.AppToolCheckBoxImpl;

/**
 *
 * @author vpc
 */
class DefaultAppToolCheckBoxBuilder<T> implements AppToolCheckBoxBuilder<T> {
    
    private String id;
    private String group;
    private GetSet<T> property;
    private T value;
    private java.util.List<String> paths = new ArrayList<String>();
    private java.util.List<AppToolComponent<AppToolCheckBox>> components = null;
    private AppToolCheckBox tool = null;
    private AbstractAppToolsBase tools;

    public DefaultAppToolCheckBoxBuilder(AbstractAppToolsBase tools) {
        this.tools = tools;
    }

    public AppToolCheckBoxBuilder<T> id(String g) {
        this.id = g;
        return this;
    }

    public AppToolCheckBoxBuilder<T> group(String g) {
        this.group = g;
        return this;
    }

    public AppToolCheckBoxBuilder<T> value(T value) {
        this.value = value;
        return this;
    }

    public AppToolCheckBoxBuilder<T> path(String... g) {
        this.paths.addAll(Arrays.asList(g));
        return this;
    }

    public AppToolCheckBoxBuilder<T> bind(WritableValue<T> s) {
        this.property = new GetSet<T>() {
            @Override
            public T get() {
                return s.get();
            }

            @Override
            public void set(T t) {
                s.set(t);
            }
        };
        return this;
    }

    public AppToolCheckBoxBuilder<T> bind(Supplier<T> s, Consumer<T> c) {
        this.property = new GetSet<T>() {
            @Override
            public T get() {
                return s.get();
            }

            @Override
            public void set(T t) {
                c.accept(t);
            }
        };
        return this;
    }

    public AppToolCheckBoxBuilder<T> build() {
        if (components != null) {
            throw new IllegalArgumentException("already build");
        }
        components = new ArrayList<>();
        String path = paths.get(0);
        AppToolComponent<AppToolCheckBox> c = null;
        {
            ItemPath ipath = ItemPath.of(path);
            String _id = id;
            if (_id == null) {
                _id = ipath.isEmpty() ? "unknown" : ipath.name();
            }
            path = ipath.toString();
            AppToolCheckBox action = new AppToolCheckBoxImpl(_id, null, tools.application, tools);
            action.title().setId(_id);
            action.smallIcon().setId("$" + _id + ".icon"); //the dollar meens the the icon key is resolved from i18n
            c = AppToolComponent.of(action, path);
            tools.addTool(c);
            components.add(c);
        }
        AppToolCheckBox tool = c.tool();
        tool.group().set(group);
        path = ItemPath.of(path).toString();
        tool.title().setId(path);
        tool.smallIcon().setId("$" + path + ".icon"); //the dollar meens the the icon key is resolved from i18n
        tool.selected().set(Objects.equals(value, property.get()));
        //property.
        tool.selected().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                boolean selected = event.getNewValue();
                if (selected) {
                    property.set(value);
                }
            }
        });
        for (int i = 1; i < paths.size(); i++) {
            components.add(c.copyTo(tools, paths.get(i)));
        }
        this.tool = tool;
        return this;
    }

    public AppToolCheckBox tool() {
        if (components == null) {
            build();
        }
        return tool;
    }

    public List<AppToolComponent<AppToolCheckBox>> components() {
        if (components == null) {
            build();
        }
        return components;
    }

    @Override
    public AppToolComponent<AppToolCheckBox> component() {
        return components().get(0);
    }
    public static interface GetSet<T> {

        T get();

        void set(T t);
    }
}

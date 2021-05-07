/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core;

import net.thevpc.echo.AppToolToggleModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppToolButtonType;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.ItemPath;
import net.thevpc.echo.swing.core.tools.AppToolCheckBoxImpl;
import net.thevpc.echo.AppToolToggle;
import net.thevpc.echo.AppToolToggleBuilder;

/**
 *
 * @author vpc
 */
class DefaultAppToolToggleBuilder implements AppToolToggleBuilder {

    private String id;
    private String group;
    private AppToolButtonType buttonType = AppToolButtonType.CHECK;
    private AppToolToggleModel property;
    private java.util.List<String> paths = new ArrayList<String>();
    private java.util.List<AppToolComponent<AppToolToggle>> components = null;
    private AppToolToggle tool = null;
    private AbstractAppToolsBase tools;

    public DefaultAppToolToggleBuilder(AbstractAppToolsBase tools) {
        this.tools = tools;
    }

    @Override
    public AppToolToggleBuilder buttonType(AppToolButtonType type) {
        this.buttonType = type == null ? AppToolButtonType.CHECK : type;
        return this;
    }

    public AppToolToggleBuilder id(String g) {
        this.id = g;
        return this;
    }

    public AppToolToggleBuilder group(String g) {
        this.group = g;
        return this;
    }

    public AppToolToggleBuilder path(String... g) {
        this.paths.addAll(Arrays.asList(g));
        return this;
    }

    @Override
    public AppToolToggleBuilder bind(WritableValue<Boolean> s) {
        this.property = new AppToolToggleModel() {
            @Override
            public boolean isSelected() {
                return s.get();
            }

            @Override
            public void setSelected(boolean b) {
                s.set(b);
            }
        };
        return this;
    }

    public <T> AppToolToggleBuilder bind(WritableValue<T> s, T value) {
        this.property = new AppToolToggleModel() {
            @Override
            public boolean isSelected() {
                return Objects.equals(s.get(), value);
            }

            @Override
            public void setSelected(boolean b) {
                if (b) {
                    s.set(value);
                }
            }
        };
        return this;
    }

    public AppToolToggleBuilder bind(AppToolToggleModel s) {
        this.property = s;
        return this;
    }

    public AppToolToggleBuilder build() {
        if (components != null) {
            throw new IllegalArgumentException("already build");
        }
        components = new ArrayList<>();
        String path = paths.get(0);
        AppToolComponent<AppToolToggle> c = null;
        ItemPath ipath = ItemPath.of(path);
        String _id = id;
        if (_id == null) {
            _id = ipath.isEmpty() ? "Action.Unknown" : "Action." + ipath.name();
        }
        path = ipath.toString();
        AppToolToggle action = new AppToolCheckBoxImpl(_id, buttonType, group, tools.application, tools);
        action.title().setId(_id);
        action.smallIcon().setId("$" + _id + ".icon"); //the dollar meens the the icon key is resolved from i18n
        c = AppToolComponent.of(action, path);
        tools.addTool(c);
        components.add(c);
        AppToolToggle tool = c.tool();
        tool.group().set(group);
        path = ItemPath.of(path).toString();
        tool.selected().set(property.isSelected());
        //property.
        tool.selected().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                boolean selected = event.getNewValue();
                property.setSelected(selected);
            }
        });
        for (int i = 1; i < paths.size(); i++) {
            components.add(c.copyTo(tools, paths.get(i)));
        }
        this.tool = tool;
        return this;
    }

    public AppToolToggle tool() {
        if (components == null) {
            build();
        }
        return tool;
    }

    public List<AppToolComponent<AppToolToggle>> components() {
        if (components == null) {
            build();
        }
        return components;
    }

    @Override
    public AppToolComponent<AppToolToggle> component() {
        return components().get(0);
    }

    public static interface GetSet<T> {

        T get();

        void set(T t);
    }
}

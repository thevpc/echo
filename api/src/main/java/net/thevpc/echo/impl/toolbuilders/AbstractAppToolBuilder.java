//package net.thevpc.echo.impl.toolbuilders;
//
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppComponentType;
//import net.thevpc.echo.api.toolbuilders.AppToolBuilder;
//import net.thevpc.echo.api.tools.AppTool;
//import net.thevpc.echo.impl.AbstractAppToolsBase;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class AbstractAppToolBuilder<T extends AppTool, C extends AppComponent, O extends AppComponentOptions, R extends AppToolBuilder>
//        implements AppToolBuilder {
//
//    protected AbstractAppToolsBase tools;
//    protected java.util.List<Path> paths = new ArrayList<Path>();
//    protected String id;
//    protected O options;
//    private java.util.List<C> components = null;
//    private T tool = null;
//    private SetValue<Integer> order;
//    private SetValue<AppComponentType> componentType;
//
//    public AbstractAppToolBuilder(AbstractAppToolsBase tools) {
//        this.tools = tools;
//    }
//
//    public R id(String g) {
//        this.id = g;
//        return (R) this;
//    }
//
//    public R path(String... g) {
//        for (String s : g) {
//            this.paths.add(Path.of(s));
//        }
//        return (R) this;
//    }
//
//    public T tool() {
//        if (components == null) {
//            build();
//        }
//        return tool;
//    }
//
//    protected R build() {
//        if (components != null) {
//            throw new IllegalArgumentException("already build");
//        }
//        AppComponentOptions options1 = options;
//        if (options1 == null && (order != null || componentType != null)) {
//            options1 = new DefaultAppComponentOptions();
//            if(order!=null){
//                options1.order(order.v);
//            }
//            if(componentType!=null){
//                options1.componentType(componentType.v);
//            }
//        }
//        C c = buildFirst(options1);
//        components = new ArrayList<>();
//        components.add(c);
//        AppTool tool = c.tool();
//        for (int i = 1; i < paths.size(); i++) {
//            components.add((C) tool.createComponent(tools, paths.get(i), options1));
//        }
//        this.tool = (T) tool;
//        return (R) this;
//    }
//
//    protected abstract C buildFirst(AppComponentOptions options);
//
//    public List<C> components() {
//        if (components == null) {
//            build();
//        }
//        return components;
//    }
//
//    public C component() {
//        return components().get(0);
//    }
//
//    @Override
//    public R options(AppComponentOptions options) {
//        this.options = (O) options;
//        if (order != null) {
//            order = null;
//        }
//        if (componentType != null) {
//            componentType = null;
//        }
//        return (R) this;
//    }
//
//    @Override
//    public R order(Integer order) {
//        if (options == null) {
//            this.order = new SetValue<>(order);
//        } else {
//            options.order(order);
//        }
//        return (R) this;
//    }
//
//    @Override
//    public R componentType(AppComponentType componentType) {
//        if (options == null) {
//            this.componentType = new SetValue<>(componentType);
//        } else {
//            options.componentType(componentType);
//        }
//        return (R) this;
//    }
//
//    private static class SetValue<T> {
//
//        T v;
//
//        public SetValue(T v) {
//            this.v = v;
//        }
//
//    }
//}

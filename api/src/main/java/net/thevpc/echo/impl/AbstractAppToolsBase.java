//package net.thevpc.echo.impl;
//
//import net.thevpc.common.props.ObservableList;
//import net.thevpc.common.props.Props;
//import net.thevpc.common.props.WritableList;
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.AppPath;
//import net.thevpc.echo.api.components.*;
//import net.thevpc.echo.api.toolbuilders.AppToolActionBuilder;
//import net.thevpc.echo.api.toolbuilders.AppToolCustomBuilder;
//import net.thevpc.echo.api.tools.AppTool;
//import net.thevpc.echo.impl.toolbuilders.DefaultAppToolActionBuilder;
//import net.thevpc.echo.impl.toolbuilders.DefaultAppToolCustomBuilder;
//import net.thevpc.echo.impl.toolbuilders.DefaultAppToolToggleBuilder;
//import net.thevpc.echo.impl.tools.ToolFolder;
//import net.thevpc.echo.impl.tools.ToolSeparator;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public abstract class AbstractAppToolsBase implements AppTools {
//
//    public WritableList<AppComponent> components = Props.of("components").listOf(AppComponent.class);
//    protected Map<String, ToolInfo> toolsMap = new HashMap<>();
//    protected Application application;
//    //    protected ToolMapResolverPropertyListener toolMapResolverAppPropertyListener = new ToolMapResolverPropertyListener();
//    private WritableList<AppTool> toolsList = Props.of("tools").listOf(AppTool.class);
//    private AppToolsConfig config = new AppToolsConfig();
//
//    public AbstractAppToolsBase(Application application) {
//        this.application = application;
//    }
//
//    public AppToolsConfig config() {
//        return config;
//    }
//
//    @Override
//    public ObservableList<AppTool> all() {
//        return toolsList.readOnly();
//    }
//
//    @Override
//    public AppTool getTool(String id) {
//        ToolInfo o = toolsMap.get(id);
//        return o == null ? null : o.tool;
//    }
//
//    @Override
//    public AppControl[] getComponents(String id) {
//        ToolInfo o = toolsMap.get(id);
//        return o == null ? new AppControl[0] : o.components.values().toArray(new AppControl[0]);
//    }
//
//    public AppContainer addFolder(AppPath path) {
//        AppComponent a = getComponent(path);
//        if (a instanceof AppContainer) {
//            return (AppContainer) a;
//        }
//        return (AppContainer) addTool(new ToolFolder(application), path, null);
//    }
//
//    @Override
//    public AppContainer addFolder(String path) {
//        return addFolder(AppPath.of(path));
//    }
//
//    public AppSeparator addSeparator(AppPath path) {
//        return (AppSeparator) addTool(
//                new ToolSeparator(application), path, null);
//    }
//
//    @Override
//    public AppSeparator addSeparator(String path) {
//        return addSeparator(AppPath.of(path));
//    }
//
//    @Override
//    public AppToolActionBuilder addAction(AppPath path) {
//        return addAction().path(path.toString());
//    }
//
//    @Override
//    public AppToolActionBuilder addAction(String path) {
//        return addAction().path(path);
//    }
//
//    @Override
//    public AppToolActionBuilder addAction() {
//        return new DefaultAppToolActionBuilder(this);
//    }
//
//    @Override
//    public AppComponent addTool(AppTool tool, String path, AppComponentOptions options) {
//        return addTool(tool, AppPath.of(path), options);
//    }
//
//    @Override
//    public ObservableList<AppComponent> components() {
//        return components.readOnly();
//    }
//
//
//    public AppToolToggleBuilder addToggle() {
//        return new DefaultAppToolToggleBuilder(this);
//    }
//
//
//    @Override
//    public AppToolCustomBuilder addCustom(String path) {
//        return addCustom().path(path);
//    }
//
//    @Override
//    public AppToolCustomBuilder addCustom() {
//        return new DefaultAppToolCustomBuilder(this);
//    }
//
//    public Application app() {
//        return application;
//    }
//
//    //    public AppToolAction addAction(Action al, String path, String... paths) {
////        return addAction().bind(al).path(path).path(paths).tool();
////    }
//    public static class ToolInfo {
//
//        private String id;
//        private AppTool tool;
//        private Map<AppPath, AppControl> components;
//    }
//
//}

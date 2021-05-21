//package net.thevpc.echo.impl;
//
//import net.thevpc.common.props.ObservableList;
//import net.thevpc.common.props.Props;
//import net.thevpc.common.props.WritableList;
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.components.*;
//import net.thevpc.echo.api.toolbuilders.AppToolActionBuilder;
//import net.thevpc.echo.api.toolbuilders.AppToolCustomBuilder;
//import net.thevpc.echo.api.model.AppComponentModel;
//import net.thevpc.echo.impl.toolbuilders.DefaultAppToolActionBuilder;
//import net.thevpc.echo.impl.toolbuilders.DefaultAppToolCustomBuilder;
//import net.thevpc.echo.impl.toolbuilders.DefaultAppToolToggleBuilder;
//import net.thevpc.echo.impl.model.ContainerModel;
//import net.thevpc.echo.impl.model.SeparatorModel;
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
//    private WritableList<AppComponentModel> toolsList = Props.of("model").listOf(AppComponentModel.class);
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
//    public ObservableList<AppComponentModel> all() {
//        return toolsList.readOnly();
//    }
//
//    @Override
//    public AppComponentModel getTool(String id) {
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
//    public AppContainer addFolder(Path path) {
//        AppComponent a = getComponent(path);
//        if (a instanceof AppContainer) {
//            return (AppContainer) a;
//        }
//        return (AppContainer) addTool(new ContainerModel(application), path, null);
//    }
//
//    @Override
//    public AppContainer addFolder(String path) {
//        return addFolder(Path.of(path));
//    }
//
//    public AppSeparator addSeparator(Path path) {
//        return (AppSeparator) addTool(
//                new SeparatorModel(application), path, null);
//    }
//
//    @Override
//    public AppSeparator addSeparator(String path) {
//        return addSeparator(Path.of(path));
//    }
//
//    @Override
//    public AppToolActionBuilder addAction(Path path) {
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
//    public AppComponent addTool(AppComponentModel tool, String path, AppComponentOptions options) {
//        return addTool(tool, Path.of(path), options);
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
//    //    public AppButtonModel addAction(Action al, String path, String... paths) {
////        return addAction().bind(al).path(path).path(paths).tool();
////    }
//    public static class ToolInfo {
//
//        private String id;
//        private AppComponentModel tool;
//        private Map<Path, AppControl> components;
//    }
//
//}

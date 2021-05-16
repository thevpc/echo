//package net.thevpc.echo;
//
//import net.thevpc.common.props.ObservableList;
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppContainer;
//import net.thevpc.echo.api.components.AppSeparator;
//import net.thevpc.echo.api.toolbuilders.AppToolActionBuilder;
//import net.thevpc.echo.api.toolbuilders.AppToolCustomBuilder;
//import net.thevpc.echo.api.tools.AppTool;
//
//public interface AppTools {
//
//    AppToolsConfig config();
//
//    ObservableList<AppTool> all();
//
//    default AppComponent getComponent(String path) {
//        return getComponent(path == null ? null : Path.of(path));
//    }
//
//    AppComponent getComponent(Path path);
//
//    AppTool getTool(String id);
//
//    AppComponent[] getComponents(String id);
//
//    AppContainer addFolder(Path path);
//
//    AppContainer addFolder(String path);
//
//    AppSeparator addSeparator(Path path);
//
//    AppSeparator addSeparator(String path);
//
//    AppToolActionBuilder addAction(Path path);
//
//    AppToolActionBuilder addAction(String path);
//
//    AppToolActionBuilder addAction();
//
//    //    @Deprecated
////    AppToolAction addAction(Action al, String path, String... paths);
//    AppComponent addTool(AppTool tool, String path, AppComponentOptions options);
//
//    AppComponent addTool(AppTool tool, Path path, AppComponentOptions options);
//
//    void removeTool(Path tool);
//
//    ObservableList<AppComponent> components();
//
//    AppToolCustomBuilder addCustom();
//
//    Application app();
//
//}

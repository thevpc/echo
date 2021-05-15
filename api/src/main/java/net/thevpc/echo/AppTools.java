//package net.thevpc.echo;
//
//import net.thevpc.common.props.ObservableList;
//import net.thevpc.echo.api.AppPath;
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
//        return getComponent(path == null ? null : AppPath.of(path));
//    }
//
//    AppComponent getComponent(AppPath path);
//
//    AppTool getTool(String id);
//
//    AppComponent[] getComponents(String id);
//
//    AppContainer addFolder(AppPath path);
//
//    AppContainer addFolder(String path);
//
//    AppSeparator addSeparator(AppPath path);
//
//    AppSeparator addSeparator(String path);
//
//    AppToolActionBuilder addAction(AppPath path);
//
//    AppToolActionBuilder addAction(String path);
//
//    AppToolActionBuilder addAction();
//
//    //    @Deprecated
////    AppToolAction addAction(Action al, String path, String... paths);
//    AppComponent addTool(AppTool tool, String path, AppComponentOptions options);
//
//    AppComponent addTool(AppTool tool, AppPath path, AppComponentOptions options);
//
//    void removeTool(AppPath tool);
//
//    ObservableList<AppComponent> components();
//
//    AppToolCustomBuilder addCustom();
//
//    Application app();
//
//}

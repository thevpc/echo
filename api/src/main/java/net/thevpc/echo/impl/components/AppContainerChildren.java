package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.tools.AppComponentModel;

import java.util.List;

public interface AppContainerChildren<T extends AppComponentModel, C extends AppComponent> extends WritableList<C> {

    C get(String id);

    AppComponent get(Path path);

    AppComponent addSeparator(Path relativePath);

    AppComponent addFolder(Path relativePath);


    /**
     * add a component with the very same model to multiple locations
     *
     * @param component    component
     * @param relativePath firstPath
     * @param all          all other paths
     * @return all components including the first one
     */
    List<AppComponent> addAll(AppComponent component, Path relativePath, Path... all);


    C add(int index, C component, String name);

    AppComponent remove(String name);

    AppComponent remove(Path relativePath);


    AppComponent add(C component, Path path);

    C add(C component, String name);

//    @Deprecated
//    AppComponent add(AppComponentModel tool, Path relativePath);

//    @Deprecated
//    List<AppComponent> addAll(AppComponentModel tool, Path relativePath, Path... all);

//    @Deprecated
//    AppComponent add(AppComponentModel tool, Path relativePath, AppComponentOptions options);

//    @Deprecated
//    default C add(T tool, String name) {
//        return add(tool, name, null);
//    }

//    @Deprecated
//    C add(T tool, String name, AppComponentOptions options);

//    @Deprecated
//    C add(int index, T tool, String name, AppComponentOptions options);
}

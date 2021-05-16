package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.tools.AppTool;

import java.util.List;

public interface AppContainerChildren<C extends AppComponent, T extends AppTool> extends WritableList<C> {

    AppComponent get(Path path);

    AppComponent addSeparator(Path relativePath);
    AppComponent add(AppTool tool, Path relativePath);

    List<AppComponent> addAll(AppTool tool, Path relativePath, Path... all);

    AppComponent add(AppTool tool, Path relativePath, AppComponentOptions options);

    default C add(T tool, String name) {
        return add(tool, name, null);
    }

    C add(T tool, String name, AppComponentOptions options);

    AppComponent add(C comp, Path path);

    C add(C comp, String name);

    C add(int index, T tool, String name, AppComponentOptions options);

    C add(int index, C comp, String name);

    AppComponent remove(String name);

    AppComponent remove(Path relativePath);
}

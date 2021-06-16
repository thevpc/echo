package net.thevpc.echo.api;

import net.thevpc.common.props.Path;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.api.components.AppComponent;

import java.util.List;

public interface AppContainerChildren<C extends AppComponent> extends WritableList<C> {

    int indexOf(String pathName);

    C get(String pathName);

    AppComponent get(Path path);

    AppComponent addSeparator();
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
    List<AppComponent> addMulti(AppComponent component, Path relativePath, Path... all);
    
    C add(int index, C component, String name);

    AppComponent remove(String name);

    AppComponent remove(Path relativePath);


    AppComponent add(C component, Path path);

    C add(C component, String name);
}

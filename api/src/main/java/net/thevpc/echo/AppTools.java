package net.thevpc.echo;

import javax.swing.Action;
import net.thevpc.common.props.ObservableList;

public interface AppTools {

    AppToolsConfig config();

    ObservableList<AppTool> all();

    default AppTool getToolByPath(String path) {
        return getToolByPath(path == null ? null : ItemPath.of(path));
    }

    AppTool getToolByPath(ItemPath path);

    AppTool getTool(String id);

    AppComponent[] getComponents(String id);

    AppToolComponent<AppToolFolder> addFolder(String path);

    AppToolComponent<AppToolSeparator> addSeparator(String path);

    AppToolActionBuilder addAction();

    @Deprecated
    AppToolAction addAction(Action al, String path, String... paths);

    <T extends AppTool> AppToolComponent<T> addTool(T tool, String path);

    AppTool addCustomTool(String id, AppComponentRenderer renderer, String path, int order);

    AppTool addHorizontalGlue(String path);

    AppTool addHorizontalStrut(String path, int size);

    AppTool addHorizontalSeparator(String path);

    AppTool addHorizontalSeparator(String path, int size);

    AppTool addVerticalGlue(String path);

    AppTool addVerticalStrut(String path, int height);

    AppTool addCustomTool(String path, AppComponentRenderer renderer);

    <T extends AppTool> void addTool(AppToolComponent<T> tool);

    <T extends AppTool> void removeTool(AppToolComponent<T> tool);

    ObservableList<AppComponent> components();

    void refresh();

    AppToolToggleBuilder addToggle();
}

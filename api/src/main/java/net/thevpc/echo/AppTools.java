package net.thevpc.echo;

import javax.swing.Action;
import net.thevpc.common.props.PList;
import net.thevpc.common.props.WritablePValue;

public interface AppTools {

    PList<AppTool> all();

    AppTool getTool(String id);

    AppComponent[] getComponents(String id);

    AppToolComponent<AppToolFolder> addFolder(String id, String path);

    AppToolComponent<AppToolFolder> addFolder(String path);

    AppToolComponent<AppToolSeparator> addSeparator(String id, String path);

    AppToolComponent<AppToolSeparator> addSeparator(String path);

    AppToolComponent<AppToolAction> addAction(String id, String path);

    void addAction(Action al, String path, String... paths);

    AppToolComponent<AppToolRadioBox> addRadio(String id, String path);

    AppToolComponent<AppToolCheckBox> addCheck(String id, String path);

    AppToolComponent<AppToolAction> addAction(String path);

    AppToolComponent<AppToolRadioBox> addRadio(String path);

    AppToolComponent<AppToolCheckBox> addCheck(String path);

    void addCheck(String id, WritablePValue<Boolean> property, String path, String... paths);

    <T> void addRadio(String id, String group, WritablePValue<T> property, T value, String path, String... paths);

    <T extends AppTool> AppToolComponent<T> addTool(T tool, String path);

    <T extends AppTool> void addTool(AppToolComponent<T> tool);

    <T extends AppTool> void removeTool(AppToolComponent<T> tool);

    PList<AppComponent> components();
    
    void refresh();
}

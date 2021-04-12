package net.thevpc.echo;

import net.thevpc.common.iconset.IconSet;
import net.thevpc.common.i18n.I18n;
import net.thevpc.common.props.*;
import net.thevpc.common.iconset.IconSets;

public interface Application extends PropertyContainer {

    ObservableValue<String> name();

    ObservableValue<AppState> state();

    Application start();

    WritableList<AppShutdownVeto> shutdownVetos();

    Application shutdown();

    AppTools tools();

    WritableValue<AppWindow> mainWindow();

    AppNode rootNode();

    ApplicationBuilder builder();

    AppHistory history();

    I18n i18n();

    AppMessages messages();

    AppLogs logs();

    IconSets iconSets();

    WritableValue<AppPropertiesTree> activeProperties();

    void runFront(Runnable run);

    void runBack(Runnable run);

    AppErrors errors();

    WritableValue<String> currentWorkingDirectory();

    AppComponentRendererFactory componentRendererFactory();

    public void waitFor();
    
    public AppDialogBuilder newDialog();
}

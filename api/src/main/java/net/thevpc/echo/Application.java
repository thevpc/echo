package net.thevpc.echo;

import net.thevpc.common.iconset.IconSet;
import net.thevpc.common.i18n.I18n;
import net.thevpc.common.props.*;
import net.thevpc.common.props.*;

public interface Application extends PropertyContainer {

    PValue<String> name();

    PValue<AppState> state();

    void start();

    WritablePList<AppShutdownVeto> shutdownVetos();

    void shutdown();

    AppTools tools();

    WritablePValue<AppWindow> mainWindow();

    AppNode[] nodes();

    ApplicationBuilder builder();

    AppHistory history();

    I18n i18n();

    AppMessages messages();

    AppLogs logs();

    WritablePLMap<String, IconSet> iconSets();

    AppIconSet iconSet();

    WritablePValue<AppPropertiesTree> activeProperties();

    void runFront(Runnable run);

    void runBack(Runnable run);

    AppErrors errors();

    WritablePValue<String> currentWorkingDirectory();

    AppComponentRendererFactory componentRendererFactory();
}

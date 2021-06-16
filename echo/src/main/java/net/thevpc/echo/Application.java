package net.thevpc.echo;

import net.thevpc.common.i18n.I18n;
import net.thevpc.common.props.*;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.api.*;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.iconset.IconSets;
import net.thevpc.echo.api.AppContainerChildren;

import java.util.concurrent.ExecutorService;

public interface Application extends Property {

    ClipboardManager clipboard();

    ApplicationLoader loader();

    WritableStr name();

    WritableList<AppFont> fonts();

    ObservableValue<AppState> state();

    ApplicationStartupConfig startupConfig();

    Application start();

    WritableList<AppShutdownVeto> shutdownVetos();

    WritableValue<ExecutorService> executorService();

    Application shutdown();

    WritableValue<AppFrame> mainFrame();

//    AppNode rootNode();
    AppContainer root();

    AppHistory history();

    I18n i18n();

    AppMessages messages();

    AppLogs logs();

    IconSets iconSets();

    WritableValue<AppPropertiesTree> activeProperties();

    void runUI(Runnable run);

    void runWorker(Runnable run);

    AppErrors errors();

    WritableString currentWorkingDirectory();

    public void waitFor();

    ApplicationToolkit toolkit();

    WritableString plaf();

    AppContainer<AppComponent> container();

    AppContainerChildren<AppComponent> components();

    WritableBoolean hideDisabled();

    PrinterService printerService();
}

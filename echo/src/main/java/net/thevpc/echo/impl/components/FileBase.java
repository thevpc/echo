package net.thevpc.echo.impl.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.AppProps;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppFileFilter;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFileControl;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public abstract class FileBase extends ControlBase implements AppFileControl {

    private WritableString currentDirectory;
    private WritableList<String> selection;
    private WritableList<AppFileFilter> filters;
    private WritableBoolean multipleValues;
    private WritableBoolean acceptFiles;
    private WritableBoolean acceptDirectories;
    public FileBase(String id, Application app,
                    Class<? extends AppComponent> componentType,
                    Class<? extends AppComponentPeer> peerType) {
        super(id,app, componentType, peerType);
        currentDirectory = AppProps.of("currentDirectory", app()).stringOf(null);
        selection = AppProps.of("selection", app()).listOf(String.class);
        filters = AppProps.of("filters", app()).listOf(AppFileFilter.class);
        multipleValues = AppProps.of("multipleValues", app()).booleanOf(false);
        acceptFiles = AppProps.of("acceptFiles", app()).booleanOf(false);
        acceptDirectories = AppProps.of("acceptDirectories", app()).booleanOf(false);
        propagateEvents(currentDirectory,selection,filters,multipleValues,acceptDirectories,acceptFiles,acceptDirectories);
    }

    public WritableString currentDirectory() {
        return currentDirectory;
    }

    public WritableBoolean multipleValues() {
        return multipleValues;
    }

    public WritableBoolean acceptFiles() {
        return acceptFiles;
    }

    public WritableBoolean acceptDirectories() {
        return acceptDirectories;
    }
    public WritableList<AppFileFilter> filters() {
        return filters;
    }

    @Override
    public WritableList<String> selection() {
        return selection;
    }

}


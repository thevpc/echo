package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolFile;
import net.thevpc.echo.api.tools.AppToolFileFilter;
import net.thevpc.echo.props.AppProps;

public class ToolFile extends AppToolBase implements AppToolFile {

    private WritableString currentDirectory;
    private WritableList<String> selection;
    private WritableList<AppToolFileFilter> filters;
    private WritableBoolean multipleValues;
    private WritableBoolean acceptFiles;
    private WritableBoolean acceptDirectories;

    public ToolFile(Application app) {
        super(null, app);
    }

    protected void init() {
        currentDirectory = AppProps.of("currentDirectory", app()).stringOf(null);
        selection = AppProps.of("selection", app()).listOf(String.class);
        filters = AppProps.of("filters", app()).listOf(AppToolFileFilter.class);
        multipleValues = AppProps.of("multipleValues", app()).booleanOf(false);
        acceptFiles = AppProps.of("acceptFiles", app()).booleanOf(false);
        acceptDirectories = AppProps.of("acceptDirectories", app()).booleanOf(false);
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
    public WritableList<AppToolFileFilter> filters() {
        return filters;
    }

    @Override
    public WritableList<String> selection() {
        return selection;
    }
}

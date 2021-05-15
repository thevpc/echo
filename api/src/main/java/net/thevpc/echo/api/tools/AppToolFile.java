package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableString;

public interface AppToolFile extends AppTool {
    WritableString currentDirectory();

    WritableBoolean multipleValues();

    WritableBoolean acceptFiles();

    WritableBoolean acceptDirectories();

    WritableList<String> selection();

    WritableList<AppToolFileFilter> filters();
}

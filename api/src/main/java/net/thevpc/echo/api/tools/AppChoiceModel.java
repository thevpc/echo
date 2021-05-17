package net.thevpc.echo.api.tools;


import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;

public interface AppChoiceModel<T> extends AppComponentModel {
    WritableList<T> values();

    WritableBoolean multipleValues();
}

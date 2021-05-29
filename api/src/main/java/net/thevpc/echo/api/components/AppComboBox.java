package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableBoolean;

public interface AppComboBox<T> extends AppChoiceControl<T>,AppEditTextControl
{

    WritableBoolean lastWasEdit();
}

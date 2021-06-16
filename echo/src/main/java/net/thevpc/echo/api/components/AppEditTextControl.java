package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableInt;
import net.thevpc.common.props.WritableString;

public interface AppEditTextControl extends AppTextControl {
    WritableString textSelection();

    WritableInt caretPosition();

    void replaceSelection(String newValue);
}

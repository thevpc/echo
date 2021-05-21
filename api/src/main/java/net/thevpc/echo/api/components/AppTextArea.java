package net.thevpc.echo.api.components;

import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppColor;

public interface AppTextArea extends AppTextControl {
    WritableString textSelection();

    WritableInt caretPosition();

    WritableBoolean rowNumberRuler();

    WritableBoolean zoomOnMouseWheel();

    WritableBoolean highlightSelectionDuplicates();

    WritableValue<AppColor> highlightSelectionDuplicatesColor();
}

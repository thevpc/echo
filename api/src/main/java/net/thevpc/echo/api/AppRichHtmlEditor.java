package net.thevpc.echo.api;

import net.thevpc.common.props.*;
import net.thevpc.echo.api.components.AppTextControl;

public interface AppRichHtmlEditor extends AppTextControl {
    WritableString textSelection();

    WritableInt caretPosition();

    WritableBoolean zoomOnMouseWheel();

    WritableBoolean highlightSelectionDuplicates();

    WritableValue<AppColor> highlightSelectionDuplicatesColor();

    String getText(int from, int to);

    int getTextLength();
}

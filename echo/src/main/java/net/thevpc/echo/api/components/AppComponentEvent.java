package net.thevpc.echo.api.components;

import net.thevpc.echo.KeyCode;
import net.thevpc.echo.api.AppEvent;

public interface AppComponentEvent extends AppEvent {

    AppEventType eventType();

    KeyCode code();

    int wheelRotation();

    boolean isShiftDown();

    boolean isControlDown();

    boolean isMetaDown();

    boolean isAltDown();

    boolean isAltGraphDown();

    boolean isMouseEvent();

    boolean isKeyEvent();

    double getMouseX();

    double getMouseY();

    boolean isPrimaryMouseButton();

    boolean isSecondaryMouseButton();

    boolean isDoubleClick();
    boolean isSimpleClick();

    int getClickCount();
    AppComponent source();
}

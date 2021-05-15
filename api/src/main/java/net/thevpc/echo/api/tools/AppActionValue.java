package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppUndoableActionSupplier;
import net.thevpc.echo.api.components.AppAction;

public interface AppActionValue extends WritableValue<AppAction> {
    void set(Runnable a);

    void setUndoable(AppUndoableActionSupplier a);
}

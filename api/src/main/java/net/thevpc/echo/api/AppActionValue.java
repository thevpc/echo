package net.thevpc.echo.api;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppUndoableActionSupplier;
import net.thevpc.echo.api.Action;

public interface AppActionValue extends WritableValue<Action> {
    void set(Runnable a);

    void setUndoable(AppUndoableActionSupplier a);
}

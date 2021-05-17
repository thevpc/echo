package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppUndoableActionSupplier;
import net.thevpc.echo.api.components.Action;

public interface AppActionValue extends WritableValue<Action> {
    void set(Runnable a);

    void setUndoable(AppUndoableActionSupplier a);
}

package net.thevpc.echo.api;

import net.thevpc.common.props.WritableValue;

public interface AppActionValue extends WritableValue<Action> {

    void unset();

    void set(Runnable a);

    void setUndoable(AppUndoableActionSupplier a);
}

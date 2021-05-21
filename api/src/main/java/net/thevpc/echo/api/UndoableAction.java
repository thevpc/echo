package net.thevpc.echo.api;

import net.thevpc.common.msg.Message;
import net.thevpc.echo.api.AppEvent;

public interface UndoableAction {

    Message doAction(AppEvent event);

    void redoAction(AppEvent event);

    void undoAction(AppEvent event);
}

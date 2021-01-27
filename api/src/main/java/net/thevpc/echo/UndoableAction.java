package net.thevpc.echo;

import net.thevpc.common.msg.Message;

public interface UndoableAction {

    Message doAction(AppEvent event);

    void redoAction(AppEvent event);

    void undoAction(AppEvent event);
}

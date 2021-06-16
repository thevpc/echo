package net.thevpc.echo.api;

import net.thevpc.common.msg.Message;
import net.thevpc.common.props.ObservableList;

public interface AppHistory {

    void doAction(UndoableAction action);

    void undoAction();

    void redoAction();

    ObservableList<Message> undoList();

    ObservableList<Message> redoList();

}

package net.thevpc.echo;

import net.thevpc.common.props.PList;
import net.thevpc.common.msg.Message;

public interface AppHistory {

    void doAction(UndoableAction action);

    void undoAction();

    void redoAction();

    PList<Message> undoList();

    PList<Message> redoList();

}

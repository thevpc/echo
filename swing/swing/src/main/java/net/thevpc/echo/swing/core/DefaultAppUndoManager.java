package net.thevpc.echo.swing.core;

import net.thevpc.echo.AppHistory;
import net.thevpc.echo.Application;
import net.thevpc.echo.UndoableAction;
import net.thevpc.common.msg.Message;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.impl.WritableStackImpl;
import net.thevpc.common.props.WritableStack;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.ObservableList;

public class DefaultAppUndoManager implements AppHistory {

    private Application application;
    private int maxEntries = 1000;
    protected WritableStack<UndoableAction> undoList = Props.of("undo").stackOf(UndoableAction.class);
    protected WritableList<Message> undoListDescription = Props.of("undoDescription").listOf(Message.class);
    protected WritableStack<UndoableAction> redoList = Props.of("redo").stackOf(UndoableAction.class);
    protected WritableList<Message> redoListDescription = Props.of("redDescription").listOf(Message.class);

    public DefaultAppUndoManager(Application application) {
        this.application = application;
    }

    @Override
    public void doAction(UndoableAction action) {
        Message m = action.doAction(new DefaultAppEvent(application, null));
        if (m != null) {
            undoList.push(action);
            undoListDescription.add(m);
            if (maxEntries > 0) {
                while (undoList.size() > maxEntries) {
                    discardUndoFirstAction();
                }
            }
        }
    }

    public void discardUndoFirstAction() {
        ((WritableStackImpl<UndoableAction>) undoList).remove(0);
        undoListDescription.remove(0);
    }

    public void discardRedoFirstAction() {
        ((WritableStackImpl<UndoableAction>) redoList).remove(0);
        redoListDescription.remove(0);
    }

    @Override
    public void undoAction() {
        UndoableAction poped = undoList.pop();
        Message msg = undoListDescription.remove(undoListDescription.size() - 1);
        poped.undoAction(new DefaultAppEvent(application, null));

        redoList.push(poped);
        redoListDescription.add(msg);

        if (maxEntries > 0) {
            while (redoList.size() > maxEntries) {
                discardRedoFirstAction();
            }
        }
    }

    @Override
    public void redoAction() {
        UndoableAction action = redoList.pop();
        Message msg = redoListDescription.remove(redoListDescription.size() - 1);
        action.redoAction(new DefaultAppEvent(application, null));
        undoList.push(action);
        undoListDescription.add(msg);
        if (maxEntries > 0) {
            while (undoList.size() > maxEntries) {
                discardUndoFirstAction();
            }
        }
    }

    @Override
    public ObservableList<Message> undoList() {
        return undoListDescription.readOnly();
    }

    @Override
    public ObservableList<Message> redoList() {
        return redoListDescription.readOnly();
    }

}

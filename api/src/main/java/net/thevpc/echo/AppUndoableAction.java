package net.thevpc.echo;

import java.awt.event.ActionEvent;

public abstract class AppUndoableAction extends AbstractAppAction {

    public AppUndoableAction(Application application, String id) {
        super(application, id);
    }

    public AppUndoableAction(Application application, String id, UndoableAction undoableAction) {
        super(application, id);
    }

    protected abstract UndoableAction createUndo();

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        application.history().doAction(createUndo());
    }

}

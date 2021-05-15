package net.thevpc.echo.swing.helpers.actions;

import java.awt.event.ActionEvent;
import net.thevpc.echo.AppEvent;
import net.thevpc.echo.AppUndoableActionSupplier;
import net.thevpc.echo.Application;
import net.thevpc.echo.UndoableAction;

public abstract class SwingAppUndoableAction extends SwingAbstractAppAction implements AppUndoableActionSupplier{

    public SwingAppUndoableAction(Application application, String id) {
        super(application, id);
    }

    public SwingAppUndoableAction(Application application, String id, UndoableAction undoableAction) {
        super(application, id);
    }

    @Override
    public UndoableAction supply(AppEvent event) {
        return createUndo();
    }

    protected abstract UndoableAction createUndo();

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        application.history().doAction(createUndo());
    }

}

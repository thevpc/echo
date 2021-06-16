package net.thevpc.echo.impl;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableValueImpl;
import net.thevpc.echo.api.UndoableAction;
import net.thevpc.echo.api.ActionEvent;
import net.thevpc.echo.api.AppUndoableActionSupplier;
import net.thevpc.echo.api.Action;
import net.thevpc.echo.api.AppActionValue;

public class AppWritableAction extends WritableValueImpl<Action> implements AppActionValue {

    public AppWritableAction(String name, Action value) {
        super(name, PropertyType.of(Action.class), value);
    }

    @Override
    public void unset() {
        set(new DoNothingAction());
    }

    @Override
    public void set(Action a) {
        super.set(new WrappedAction(a));
    }

    @Override
    public void set(Runnable a) {
        super.set(new WrappedRunnable(a));
    }

    @Override
    public void setUndoable(AppUndoableActionSupplier a) {
        super.set(new WrappedUndoableAction(a));
    }

    private static class DoNothingAction implements Action {

        public DoNothingAction() {
        }

        @Override
        public void run(ActionEvent event) {
        }
    }

    private static class WrappedAction implements Action {

        private final Action a;

        public WrappedAction(Action a) {
            this.a = a;
        }

        @Override
        public void run(ActionEvent event) {
            if (a != null) {
                try {
                    a.run(event);
                } catch (Exception ex) {
                    event.app().errors().add(ex);
                }
            }
        }
    }

    private static class WrappedRunnable implements Action {

        private final Runnable a;

        public WrappedRunnable(Runnable a) {
            this.a = a;
        }

        @Override
        public void run(ActionEvent event) {
            if (a != null) {
                try {
                    a.run();
                } catch (Exception ex) {
                    event.app().errors().add(ex);
                }
            }
        }
    }

    private static class WrappedUndoableAction implements Action {

        private final AppUndoableActionSupplier a;

        public WrappedUndoableAction(AppUndoableActionSupplier a) {
            this.a = a;
        }

        @Override
        public void run(ActionEvent event) {
            if (a != null) {
                UndoableAction r = null;
                try {
                    r = a.supply(event);
                } catch (Exception ex) {
                    event.app().errors().add(ex);
                }
                if (r != null) {
                    event.app().history().doAction(r);
                }
            }
        }
    }
}

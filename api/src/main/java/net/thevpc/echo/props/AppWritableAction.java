package net.thevpc.echo.props;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableValueImpl;
import net.thevpc.echo.AppActionEvent;
import net.thevpc.echo.AppUndoableActionSupplier;
import net.thevpc.echo.UndoableAction;
import net.thevpc.echo.api.components.AppAction;
import net.thevpc.echo.api.tools.AppActionValue;

public class AppWritableAction extends WritableValueImpl<AppAction> implements AppActionValue {
    public AppWritableAction(String name, AppAction value) {
        super(name, PropertyType.of(AppAction.class), value);
    }
    @Override
    public void set(AppAction a) {
        super.set(new AppAction() {
            @Override
            public void run(AppActionEvent event) {
                if (a != null) {
                    try {
                        a.run(event);
                    } catch (Exception ex) {
                        event.app().errors().add(ex);
                    }
                }
            }
        });
    }

    @Override
    public void set(Runnable a){
        super.set(new AppAction() {
            @Override
            public void run(AppActionEvent event) {
                if (a != null) {
                    try {
                        a.run();
                    } catch (Exception ex) {
                        event.app().errors().add(ex);
                    }
                }
            }
        });
    }

    @Override
    public void setUndoable(AppUndoableActionSupplier a){
        super.set(new AppAction() {
            @Override
            public void run(AppActionEvent event) {
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
        });
    }
}

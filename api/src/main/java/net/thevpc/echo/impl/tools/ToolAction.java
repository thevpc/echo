package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.components.AppAction;
import net.thevpc.echo.api.tools.AppActionValue;
import net.thevpc.echo.api.tools.AppToolAction;
import net.thevpc.echo.props.AppWritableAction;

public class ToolAction extends AppToolBase implements AppToolAction {

    private AppActionValue action = new AppWritableAction("action",null);

    public ToolAction(String id, Application app) {
        super(id, app);
        init(id);
    }

    public ToolAction(String id, AppAction a,Application app) {
        super(id, app);
        init(id);
        action.set(a);
    }
    public ToolAction(String id, Runnable a,Application app) {
        super(id, app);
        init(id);
        action.set(a);
    }

    public ToolAction(Application app) {
        super(null, app);
    }

    protected void init(String id){
        if(id!=null){
            String aid = "Action." + id;
            title().set(Str.i18n(aid));
            smallIcon().set(Str.i18n(aid+".icon"));
        }
    }
    @Override
    public AppActionValue action() {
        return action;
    }

}

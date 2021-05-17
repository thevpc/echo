package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.Action;
import net.thevpc.echo.api.tools.AppActionValue;
import net.thevpc.echo.api.tools.AppToolButtonModel;
import net.thevpc.echo.props.AppWritableAction;

public class ButtonModel extends AppComponentModelBase implements AppToolButtonModel {

    private AppActionValue action = new AppWritableAction("action",null);

    public ButtonModel(String id, Application app) {
        super(id, app);
        init(id);
    }

    public ButtonModel(String id, Action a, Application app) {
        super(id, app);
        init(id);
        action.set(a);
    }
    public ButtonModel(String id, Runnable a, Application app) {
        super(id, app);
        init(id);
        action.set(a);
    }

    public ButtonModel(Application app) {
        super(null, app);
    }

    protected void init(String id){
        if(id!=null){
            String aid = "Action." + id;
            title().set(Str.i18n(aid));
            smallIcon().set(Str.i18n(aid+".icon"));
        }
        propagateEvents(action);
    }
    @Override
    public AppActionValue action() {
        return action;
    }

}

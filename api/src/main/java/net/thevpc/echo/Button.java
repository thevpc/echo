package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.Action;
import net.thevpc.echo.api.AppActionValue;
import net.thevpc.echo.api.components.AppButton;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.AppWritableAction;
import net.thevpc.echo.impl.components.TextBase;
import net.thevpc.echo.spi.peers.AppButtonPeer;

public class Button extends TextBase implements AppButton {
    private AppActionValue action = new AppWritableAction("action", null);

    public Button(Application app) {
        this(null, (Str) null, app);
    }

    public Button(String id, Application app) {
        this(id, (Str) null, app);
    }

    public Button(String id, Action a, Application app) {
        this(id, (Str) null, app);
        action.set(a);
    }

    public Button(String id, Runnable a, Application app) {
        this(id, (Str) null, app);
        action.set(a);
    }

    public Button(String id, Str txt, Application app) {
        super(id, txt, app, AppButton.class, AppButtonPeer.class);
        if (id != null) {
            String aid = "Action." + id;
            text().set(Str.i18n(aid));
            icon().set(Str.i18n(aid + ".icon"));
        }
        propagateEvents(action);
    }

    @Override
    public AppActionValue action() {
        return action;
    }

    @Override
    public AppComponent copy(boolean bind) {
        Button c=(Button) super.copy(bind);
        if(bind) {
            text().bindTarget(c.text());
            icon().bindTarget(c.icon());
            action().bindTarget(c.action());
            tooltip().bindTarget(c.tooltip());
        }
        return c;
    }
}


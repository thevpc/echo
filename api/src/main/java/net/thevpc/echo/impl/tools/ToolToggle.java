package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.Application;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.tools.AppToolToggle;

public class ToolToggle extends AppToolBase implements AppToolToggle {

    private final WritableString group = Props.of("group").stringOf(null);
    private final WritableBoolean selected = Props.of("selected").booleanOf(false);

    public ToolToggle(Application app) {
        this(null,null, app);
    }

//    public ToolToggle(String group, Application app) {
//        this(null, group, app);
//    }

    public ToolToggle(String id, String group, Application app) {
        super(id, app);
        group().set(group);
        if (id != null) {
            String aid = "Action." + id;
            title().set(Str.i18n(aid));
            smallIcon().set(Str.i18n(aid + ".icon"));
        }
        propagateEvents(this.group, selected);
    }

    @Override
    public WritableString group() {
        return group;
    }

    @Override
    public WritableBoolean selected() {
        return selected;
    }
}

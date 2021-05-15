package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.tools.AppToolToggle;

public class ToolToggle extends AppToolBase implements AppToolToggle {

    private final WritableString group = Props.of("group").stringOf(null);
    private final WritableBoolean selected = Props.of("selected").booleanOf(false);

    public ToolToggle(Application app) {
        super(null, app);
    }
    public ToolToggle(String id,Application app) {
        super(id, app);
        init(id);
    }

    public ToolToggle(String id,String group,Application app) {
        super(id, app);
        init(id);
        group().set(group);
    }

    protected void init(String id){
        if(id!=null){
            String aid = "Action." + id;
            title().set(Str.i18n(aid));
            smallIcon().set(Str.i18n(aid+".icon"));
        }
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

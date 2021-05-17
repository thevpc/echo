package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.echo.api.tools.AppUserControlModel;

public class UserControlModel extends AppComponentModelBase implements AppUserControlModel {
    private WritableValue<Object> renderer= Props.of("renderer").valueOf(Object.class);
    public UserControlModel(Application app) {
        this(null,app);
    }
    public UserControlModel(String id, Application app) {
        super(id, app);
        propagateEvents(this.renderer);
    }

    @Override
    public WritableValue<Object> renderer() {
        return renderer;
    }
}

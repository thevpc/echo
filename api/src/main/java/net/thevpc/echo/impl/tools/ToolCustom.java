package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.echo.api.tools.AppToolCustom;

public class ToolCustom extends AppToolBase implements AppToolCustom {
    private WritableValue<Object> renderer= Props.of("renderer").valueOf(Object.class);
    public ToolCustom(String id, Object renderer, Application app) {
        super(id, app);
        this.renderer.set(renderer);
    }

    @Override
    public WritableValue<Object> renderer() {
        return renderer;
    }
}

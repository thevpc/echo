package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTemporalField;
import net.thevpc.echo.api.tools.AppToolTemporal;
import net.thevpc.echo.impl.tools.ToolTemporal;

import java.time.temporal.Temporal;

public class TemporalField<T extends Temporal> extends AppComponentBase implements AppTemporalField {
    public TemporalField(AppToolTemporal<T> tool) {
        super(tool);
    }

    public TemporalField(Class<T> type,Application app) {
        super(new ToolTemporal<T>(type,app));
    }

    public AppToolTemporal<T> tool() {
        return (AppToolTemporal<T>) super.tool();
    }

}


package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTemporalField;
import net.thevpc.echo.api.peers.AppTemporalFieldPeer;
import net.thevpc.echo.api.tools.AppTemporalFieldModel;
import net.thevpc.echo.impl.tools.TemporalFieldModel;

import java.time.temporal.Temporal;

public class TemporalField<T extends Temporal> extends AppControlBase implements AppTemporalField {
    public TemporalField(AppTemporalFieldModel<T> tool) {
        super(tool
                , AppTemporalFieldModel.class, AppTemporalField.class, AppTemporalFieldPeer.class
        );
    }

    public TemporalField(Class<T> type,Application app) {
        this(new TemporalFieldModel<T>(type,app));
    }

    public AppTemporalFieldModel<T> model() {
        return (AppTemporalFieldModel<T>) super.model();
    }

}


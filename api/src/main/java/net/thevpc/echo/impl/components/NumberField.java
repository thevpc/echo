package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppNumberField;
import net.thevpc.echo.api.peers.AppNumberFieldPeer;
import net.thevpc.echo.api.tools.AppNumberFieldModel;
import net.thevpc.echo.impl.tools.NumberFieldModel;

public class NumberField extends AppControlBase implements AppNumberField {
    public NumberField(AppNumberFieldModel tool) {
        super(tool
                , AppNumberFieldModel.class, AppNumberField.class, AppNumberFieldPeer.class
        );
    }

    public NumberField(Application app) {
        this(new NumberFieldModel(app));
    }

    public AppNumberFieldModel model() {
        return (AppNumberFieldModel) super.model();
    }

}


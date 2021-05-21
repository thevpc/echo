package net.thevpc.echo.impl.dialog;

import net.thevpc.echo.*;
import net.thevpc.echo.api.AppDialogInputPanel;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.constraints.ParentMargin;

public class InputTextAreaPanel extends VerticalPane implements AppDialogInputPanel {

    private Label header;
    private TextArea value;
    private Application app;

    public InputTextAreaPanel(Application app, Str headerId, Str initialValue) {
        super(app);
        this.app = app;
        parentConstraints().addAll(new ParentMargin(5, 5, 5, 5));
        header = new Label(app);
        value = new TextArea(app);
        header.text().set(headerId);
        value.text().set(initialValue);
        children().addAll(header, value);
    }

    @Override
    public AppComponent getComponent() {
        return this;
    }

    @Override
    public Object getValue() {
        return value.text().get().value(app.i18n());
    }

}

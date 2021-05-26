package net.thevpc.echo.impl.dialog;

import net.thevpc.echo.*;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.constraints.AllMargins;
import net.thevpc.echo.api.AppDialogInputPane;
import net.thevpc.echo.constraints.AllAnchors;
import net.thevpc.echo.constraints.AllFill;
import net.thevpc.echo.constraints.AllGrow;

public class InputTextFieldPanel extends GridPane implements AppDialogInputPane {

    private Label header;
    private TextField value;
    private Application app;

    public InputTextFieldPanel(Application app, Str headerId, Str initialValue) {
        super(1,app);
        this.app = app;
        parentConstraints().addAll(AllMargins.of(5, 5, 5, 5),AllFill.HORIZONTAL,AllAnchors.LEFT,AllGrow.HORIZONTAL);
        header = new Label(app);
        value = new TextField(app);
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
        return value.text().get().value(app.i18n(),locale().get());
    }

}

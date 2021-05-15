package net.thevpc.echo.impl.dialog;

import net.thevpc.echo.AppDialogInputPanel;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.constraints.ParentDirection;
import net.thevpc.echo.constraints.ParentLayout;
import net.thevpc.echo.constraints.ParentMargin;
import net.thevpc.echo.impl.components.Label;
import net.thevpc.echo.impl.components.Panel;
import net.thevpc.echo.impl.components.TextField;

public class InputTextFieldPanel extends Panel implements AppDialogInputPanel {

    private Label header;
    private TextField value;
    private Application app;

    public InputTextFieldPanel(Application app, Str headerId, Str initialValue) {
        super(app);
        this.app = app;
        constraints().addAll(ParentLayout.LINEAR, ParentDirection.VERTICAL, new ParentMargin(5, 5, 5, 5));
        header = new Label(app);
        value = new TextField(app);
        header.tool().text().set(headerId);
        value.tool().text().set(initialValue);
        children().addAll(header, value);
    }

    @Override
    public AppComponent getComponent() {
        return this;
    }

    @Override
    public Object getValue() {
        return value.tool().text().get().getValue(app);
    }

}

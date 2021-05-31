package net.thevpc.echo.impl.dialog;

import net.thevpc.echo.*;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.constraints.*;
import net.thevpc.echo.api.AppDialogInputPane;

public class InputTextAreaPanel extends GridPane implements AppDialogInputPane {

    private Label header;
    private TextArea value;
    private Application app;

    public InputTextAreaPanel(Application app, Str headerId, Str initialValue) {
        super(1,app);
        this.app = app;
        parentConstraints().addAll(AllMargins.of(5, 5, 5, 5),
                AllFill.HORIZONTAL,AllAnchors.LEFT,AllGrow.HORIZONTAL,
                ContainerGrow.ALL
                );
        header = new Label(app);
        value = new TextArea(app);
        header.text().set(headerId);
        value.text().set(initialValue);
        children().addAll(header, 
                new ScrollPane(value)
                    .with(s->s.childConstraints().addAll(Grow.BOTH, Fill.BOTH))
        );
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

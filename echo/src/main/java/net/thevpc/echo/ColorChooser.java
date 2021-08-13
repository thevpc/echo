package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.AppAlertInputPane;
import net.thevpc.echo.api.AppAlertResult;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.components.ColorBase;
import net.thevpc.echo.spi.peers.AppColorChooserPeer;

public class ColorChooser extends ColorBase implements AppColorChooser, AppAlertInputPane {
    public ColorChooser(Application app) {
        this(null, app);
    }

    public ColorChooser(String id, Application app) {
        super(id, app, AppColorChooser.class, AppColorChooserPeer.class);
    }

    public AppAlertResult showDialog(AppComponent owner) {
        Alert alert = owner == null ? new Alert(app()) : new Alert(owner);
        alert.title().set(Str.i18n("ColorChooser.title"));
        alert.headerText().set(Str.i18n("ColorChooser.header"));
        alert.headerIcon().set(Str.of("colors"));
        alert.withButtons("selectColor", "selectNoColor", "cancel");
        alert.defaultButton().set("selectColor");
        alert.content().set(this);
        return alert.showDialog(owner);
    }

    @Override
    public AppColor getValue() {
        return value().get();
    }
}


package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.SimpleProperty;
import net.thevpc.echo.*;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.props.AppProps;
import net.thevpc.echo.iconset.WritableImage;

import java.util.UUID;

public class AppComponentModelBase extends SimpleProperty implements AppComponentModel {

    private final WritableBoolean enabled;
    private final WritableBoolean visible;
    private final WritableInt mnemonic;
    private final WritableString accelerator;
    private final WritableStr title;
    private final WritableStr tooltip;
    private final WritableImage smallIcon;
    private final WritableImage largeIcon;
    private String id;
    private Application app;
    protected WritableMap<Object,Object> properties= Props.of("properties").mapOf(Object.class,Object.class);

    public AppComponentModelBase(String id, Application app) {
        this(id, app, true);
    }
    public AppComponentModelBase(Application app) {
        this(null, app, true);
    }

    public AppComponentModelBase(String id, Application app, boolean doConfig) {
        super(id==null? UUID.randomUUID().toString():id);
        this.id = propertyName();
        this.app = app;
        enabled = AppProps.of("enabled", app).booleanOf(true);
        visible = AppProps.of("visible", app).booleanOf(true);
        title = AppProps.of("title", app).strOf(Str.i18n(id));
        tooltip = AppProps.of("tooltip", app).strOf(
                doConfig/*&&tools.config().configurableTooltip().get()*/ ? Str.i18n(id + ".tooltip") : null);
        smallIcon = AppProps.of("smallIcon", app).iconOf(
                doConfig/*&&tools.config().configurableSmallIcon().get()*/?
                        Str.i18n(id + ".icon"):null);
        largeIcon = AppProps.of("largeIcon", app).iconOf(
                doConfig/*&&tools.config().configurableLargeIcon().get()*/?
                        Str.i18n(id + ".largeIcon"):null
        );
        accelerator = AppProps.of("accelerator", app).stringOf(null);
        mnemonic = AppProps.of("mnemonic", app).intOf(0);
        propagateEvents(enabled,visible,title,tooltip,smallIcon,largeIcon,accelerator,mnemonic);
    }

    public WritableInt mnemonic() {
        return mnemonic;
    }

    public WritableString accelerator() {
        return accelerator;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public WritableImage smallIcon() {
        return smallIcon;
    }

    @Override
    public WritableImage largeIcon() {
        return largeIcon;
    }

    @Override
    public WritableBoolean enabled() {
        return enabled;
    }

    @Override
    public WritableBoolean visible() {
        return visible;
    }

    @Override
    public WritableStr title() {
        return title;
    }

    @Override
    public WritableStr tooltip() {
        return tooltip;
    }

    @Override
    public WritableMap<Object,Object> properties() {
        return properties;
    }

    public Application app() {
        return app;
    }
}

package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.WritableMapImpl;
import net.thevpc.echo.*;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.WritableStr;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.props.AppProps;
import net.thevpc.echo.props.WritableImage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AppToolBase implements AppTool {

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

    public AppToolBase(String id, Application app) {
        this(id, app, true);
    }
    public AppToolBase(Application app) {
        this(null, app, true);
    }

    public AppToolBase(String id, Application app, boolean doConfig) {
        this.id = id==null? UUID.randomUUID().toString():id;
        this.app = app;
        enabled = AppProps.of("enabled", app).booleanOf(true);
        visible = AppProps.of("visible", app).booleanOf(true);
        title = AppProps.of("title", app).strOf(Str.i18n(id));
        tooltip = AppProps.of("tooltip", app).strOf(
                doConfig/*&&tools.config().configurableTooltip().get()*/ ? Str.i18n(id + ".tooltip") : null);
        smallIcon = AppProps.of("smallIcon", app).iconIdOf(
                doConfig/*&&tools.config().configurableSmallIcon().get()*/?
                        Str.i18n(id + ".icon"):null);
        largeIcon = AppProps.of("largeIcon", app).iconIdOf(
                doConfig/*&&tools.config().configurableLargeIcon().get()*/?
                        Str.i18n(id + ".largeIcon"):null
        );
        accelerator = AppProps.of("accelerator", app).stringOf(null);
        mnemonic = AppProps.of("mnemonic", app).intOf(0);
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

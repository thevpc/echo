package net.thevpc.echo;

import net.thevpc.echo.props.AppProps;
import net.thevpc.echo.props.AppWritableIcon;
import net.thevpc.echo.props.AppWritableString;
import net.thevpc.common.props.WritableValue;

public abstract class AbstractAppTool implements AppTool {

    private final WritableValue<Boolean> enabled;
    private final WritableValue<Boolean> visible;
    private final WritableValue<Integer> mnemonic;
    private final WritableValue<String> accelerator;
    private final AppWritableString title;
    private final AppWritableString tooltip;
    private final AppWritableIcon smallIcon;
    private final AppWritableIcon largeIcon;
    private String id;
    private Application app;
    private AppTools tools;

    public AbstractAppTool(String id, Application app, AppTools tools) {
        this(id, app, tools,true);
    }
    
    public AbstractAppTool(String id, Application app, AppTools tools,boolean doConfig) {
        this.id = id;
        this.tools = tools;
        this.app = app;
        enabled = AppProps.of("enabled", app).valueOf(Boolean.class, true);
        visible = AppProps.of("visible", app).valueOf(Boolean.class, true);
        title = AppProps.of("title", app).strIdOf(id);
        tooltip = AppProps.of("tooltip", app).strIdOf(
                doConfig&&tools.config().configurableTooltip().get() ? (id + ".tooltip") : null);
        smallIcon = AppProps.of("smallIcon", app).iconIdOf(
                doConfig&&tools.config().configurableSmallIcon().get()?
                ("$" + id + ".icon"):null); //the dollar meens the the icon key is resolved from i18n
        largeIcon = AppProps.of("largeIcon", app).iconIdOf(
                doConfig&&tools.config().configurableLargeIcon().get()?
                (id + ".largeIcon"):null
        );
        accelerator = AppProps.of("accelerator", app).valueOf(String.class, null);
        mnemonic = AppProps.of("mnemonic", app).valueOf(Integer.class, 0);
    }

    @Override
    public AppTools tools() {
        return tools;
    }

    public WritableValue<Integer> mnemonic() {
        return mnemonic;
    }

    public WritableValue<String> accelerator() {
        return accelerator;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public AppWritableIcon smallIcon() {
        return smallIcon;
    }

    @Override
    public AppWritableIcon largeIcon() {
        return largeIcon;
    }

    @Override
    public WritableValue<Boolean> enabled() {
        return enabled;
    }

    @Override
    public WritableValue<Boolean> visible() {
        return visible;
    }

    @Override
    public AppWritableString title() {
        return title;
    }

    @Override
    public AppWritableString tooltip() {
        return tooltip;
    }

    @Override
    public <T extends AppTool> AppToolComponent<T> copyTo(AppTools tools, String newPath) {
        AppToolComponent<T> c2 = AppToolComponent.of((T) this, newPath);
        tools.addTool(c2);
        return c2;
    }

}

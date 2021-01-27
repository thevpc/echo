package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritablePValue;

public abstract class AbstractAppTool implements AppTool {

    private WritablePValue<Boolean> enabled = Props.of("enabled").valueOf(Boolean.class, true);
    private WritablePValue<Boolean> visible = Props.of("visible").valueOf(Boolean.class, true);
    private WritablePValue<String> title = Props.of("title").valueOf(String.class, "");
    private WritablePValue<String> smallIcon = Props.of("smallIcon").valueOf(String.class, null);
    private WritablePValue<String> largeIcon = Props.of("largeIcon").valueOf(String.class, null);
    private String id;

    public AbstractAppTool(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public WritablePValue<String> smallIcon() {
        return smallIcon;
    }

    @Override
    public WritablePValue<String> largeIcon() {
        return largeIcon;
    }

    @Override
    public WritablePValue<Boolean> enabled() {
        return enabled;
    }

    @Override
    public WritablePValue<Boolean> visible() {
        return visible;
    }

    @Override
    public WritablePValue<String> title() {
        return title;
    }

    @Override
    public <T extends AppTool> AppToolComponent<T> copyTo(AppTools tools, String newPath) {
        AppToolComponent<T> c2 = AppToolComponent.of((T) this, newPath);
        tools.addTool(c2);
        return c2;
    }

}

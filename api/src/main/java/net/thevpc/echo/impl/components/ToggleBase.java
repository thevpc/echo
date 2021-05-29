package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppToggleControl;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class ToggleBase extends TextBase implements AppToggleControl {

    private final WritableString group = Props.of("group").stringOf(null);
    private final WritableBoolean selected = Props.of("selected").booleanOf(false);

    public ToggleBase(String id, Str str, String group, Application app,
                      Class<? extends AppToggleControl> componentType,
                      Class<? extends AppComponentPeer> peerType) {
        super(id, str, app, componentType, peerType);
        if (id != null && !id.startsWith(".")) {
            String aid = "Action." + id;
            text().set(str == null ? Str.i18n(aid) : str);
            icon().set(Str.i18n(aid + ".icon"));
        } else {
            text().set(str == null ? Str.of("") : str);
        }
        group().set(group);
        propagateEvents(this.group, selected);
    }

    @Override
    public WritableString group() {
        return group;
    }

    @Override
    public WritableBoolean selected() {
        return selected;
    }

}


package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.ObservableBoolean;
import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.api.components.AppButtonContainer;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class ButtonGroupBase extends ContainerBase<AppComponent> implements AppButtonContainer {
    private WritableStr text;
    private WritableTextStyle textStyle;
    private WritableString textContentType;
    private WritableBoolean actionable;

    public ButtonGroupBase(String id, Str text, Application app,
                           Class<? extends AppComponent> componentType,
                           Class<? extends AppComponentPeer> peerType
                    ) {
        super(id,app,componentType,peerType,AppComponent.class);
        this.text = AppProps.of("text", app()).strOf(Str.of(null));
        actionable = AppProps.of("actionable", app()).booleanOf(true);
        textContentType = AppProps.of("textContentType", app()).stringOf("text/plain");
        textStyle = new WritableTextStyle("textStyle");
        if (id != null) {
            String aid = "Action." + id;
            text().set(text==null?Str.i18n(aid):text);
            smallIcon().set(Str.i18n(aid + ".icon"));
        }else{
            text().set(text == null ? Str.of("") : text);
        }
        propagateEvents(this.text, textStyle);
    }

    public WritableString textContentType() {
        return textContentType;
    }

    public WritableStr text() {
        return text;
    }

    public ObservableBoolean actionable() {
        return actionable;
    }

    public WritableTextStyle textStyle() {
        return textStyle;
    }
}


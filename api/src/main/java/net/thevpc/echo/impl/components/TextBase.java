package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.AppProps;
import net.thevpc.echo.Application;
import net.thevpc.echo.WritableTextStyle;
import net.thevpc.echo.api.components.AppTextControl;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class TextBase extends ControlBase implements AppTextControl {
    private WritableString textContentType;
    private WritableStr text;
    private WritableTextStyle textStyle;
    public TextBase(String id, Str txt, Application app,
                    Class<? extends AppTextControl> componentType,
                    Class<? extends AppComponentPeer> peerType) {
        super(id
                , app, componentType, peerType
        );
        text = AppProps.of("text",app()).strOf( null);
        textContentType = AppProps.of("text",app()).stringOf( "text/plain");
        textStyle = new WritableTextStyle("textStyle");
        text.set(txt==null? Str.empty():txt);
        propagateEvents(text,textStyle);
    }

    @Override
    public WritableString textContentType() {
        return textContentType;
    }

    @Override
    public WritableStr text() {
        return text;
    }

    @Override
    public WritableTextStyle textStyle() {
        return textStyle;
    }
}
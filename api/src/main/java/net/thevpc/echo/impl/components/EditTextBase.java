package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableInt;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppEditTextControl;
import net.thevpc.echo.api.components.AppTextControl;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public abstract class EditTextBase extends TextBase implements AppEditTextControl {
    private WritableString textSelection = Props.of("textSelection").stringOf("");
    private WritableInt caretPosition = Props.of("caretPosition").intOf(-1);

    public EditTextBase(String id, Str txt, Application app,
                        Class<? extends AppTextControl> componentType,
                        Class<? extends AppComponentPeer> peerType) {
        super(id, txt, app, componentType, peerType);

        propagateEvents(textSelection, caretPosition);
    }

    @Override
    public WritableString textSelection() {
        return textSelection;
    }

    @Override
    public WritableInt caretPosition() {
        return caretPosition;
    }


}
package net.thevpc.echo.api.components;

import net.thevpc.echo.spi.peers.AppTextFieldPeer;

public interface AppTextField extends AppEditTextControl {
    AppTextFieldPeer peer();
}

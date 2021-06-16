package net.thevpc.echo.spi.peers;

import net.thevpc.echo.api.components.AppTreeCallBack;

public interface AppTreePeer extends AppComponentPeer{

    <T> AppTreeCallBack<T> callback();
}

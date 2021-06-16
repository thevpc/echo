package net.thevpc.echo.spi.peers;

public interface AppWebViewPeer extends AppComponentPeer{

    void navigate(String url);
    void refresh();
}

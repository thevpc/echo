package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppWebView;
import net.thevpc.echo.impl.components.TextBase;
import net.thevpc.echo.spi.peers.AppWebViewPeer;

public class WebView extends TextBase implements AppWebView {
    public WebView(Application app) {
        this(null,null,app);
    }

    public WebView(Str text, Application app) {
        this(null,text,app);
    }

    public WebView(String id, Str text, Application app) {
        super(id,text,app, AppWebView.class, AppWebViewPeer.class);
    }

    @Override
    public AppWebViewPeer peer() {
        return (AppWebViewPeer) super.peer();
    }

    public void navigate(String url){
        peer().navigate(url);
    }

    public void refresh() {
        peer().refresh();
    }

}


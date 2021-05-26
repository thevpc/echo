package net.thevpc.echo.swing.peers;

import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppWebViewPeer;

import javax.swing.*;
import java.net.URL;

public class SwingWebView implements SwingPeer, AppWebViewPeer {
    private JEditorPane browser;

    @Override
    public void install(AppComponent comp) {
        browser = new JEditorPane();
        browser.setEditable(false);
    }

    @Override
    public Object toolkitComponent() {
        return browser;
    }

    @Override
    public void navigate(String url) {
        try {
            if(url==null || url.isEmpty()){
                browser.setText("");
            }else {
                browser.setPage(new URL(url));
            }
        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }

    @Override
    public void refresh() {
        try {
            URL old = browser.getPage();
            navigate(null);
            if(old!=null){
                browser.setPage(old);
            }
        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }
}

//package net.thevpc.echo.swing.peers;
//
//import net.thevpc.echo.UncheckedException;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.spi.peers.AppWebViewPeer;
//import org.lobobrowser.LoboBrowser;
//import org.lobobrowser.gui.BrowserPanel;
//import org.lobobrowser.reuse.ReuseManager;
//import org.lobobrowser.security.TrustManager;
//
//import javax.net.ssl.SSLSocketFactory;
//import java.net.MalformedURLException;
//
//public class SwingWebViewLoboBrowser implements SwingPeer, AppWebViewPeer {
//    private static final SSLSocketFactory socketFactory = TrustManager.makeSSLSocketFactory(ReuseManager.class.getResourceAsStream("/trustStore.certs"));
//    static {
//        try {
//            LoboBrowser.getInstance().initLogging(false);
//            LoboBrowser.getInstance().init(false, false,socketFactory);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//    private BrowserPanel browser;
//    @Override
//    public void install(AppComponent comp) {
//        browser = new BrowserPanel();
//    }
//
//    @Override
//    public void navigate(String url){
//        try {
//            browser.navigate(url);
//        } catch (MalformedURLException e) {
//            throw UncheckedException.wrap(e);
//        }
//    }
//
//    @Override
//    public void refresh() {
//        browser.reload();
//    }
//
//    @Override
//    public Object toolkitComponent() {
//        return browser;
//    }
//}

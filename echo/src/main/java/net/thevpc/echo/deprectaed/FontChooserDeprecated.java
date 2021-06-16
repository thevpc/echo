//package net.thevpc.echo.deprectaed;
//
//import net.thevpc.echo.Application;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppFontChooser;
//import net.thevpc.echo.impl.components.FontBase;
//import net.thevpc.echo.spi.peers.AppFontChooserPeer;
//
//public class FontChooserDeprecated extends FontBase implements AppFontChooser {
//
//    public FontChooserDeprecated(Application app) {
//        this(null, app);
//    }
//
//    public FontChooserDeprecated(String id, Application app) {
//        super(id, app, AppFontChooser.class, AppFontChooserPeer.class);
//    }
//
//    @Override
//    public AppFontChooserPeer peer() {
//        return (AppFontChooserPeer) super.peer();
//    }
//
//    @Override
//    public boolean showDialog(AppComponent owner) {
//        return peer().showDialog(owner);
//    }
//}

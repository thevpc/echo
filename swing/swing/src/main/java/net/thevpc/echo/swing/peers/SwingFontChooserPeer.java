//package net.thevpc.echo.swing.peers;
//
//import net.thevpc.common.i18n.Str;
//import net.thevpc.common.swing.font.JFontChooser;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppFontChooser;
//import net.thevpc.echo.spi.peers.AppFontChooserPeer;
//import net.thevpc.echo.swing.SwingPeerHelper;
//import net.thevpc.echo.swing.helpers.SwingHelpers;
//
//import java.awt.*;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//
//public class SwingFontChooserPeer implements AppFontChooserPeer, SwingPeer {
//    private JFontChooser swingFontChooser;
//    private AppFontChooser appFontChooser;
//
//    @Override
//    public void install(AppComponent comp) {
//        this.appFontChooser = (AppFontChooser) comp;
//        this.swingFontChooser = new JFontChooser(null);
//        SwingPeerHelper.installComponent(appFontChooser, swingFontChooser);
//        this.appFontChooser.selection().onChangeAndInit(() -> {
//            this.swingFontChooser.setSelectedFont(
//                    SwingHelpers.toAwtFont(this.appFontChooser.selection().get())
//            );
//        });
//        swingFontChooser.addPropertyChangeListener("selectedFont", new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                appFontChooser.selection().set(
//                        SwingHelpers.fromAwtFont(swingFontChooser.getSelectedFont(), appFontChooser.app())
//                );
//            }
//        });
//    }
//
//    @Override
//    public boolean showDialog(AppComponent owner) {
//        Component ow = SwingPeerHelper.resolveOwnerComponent(owner, appFontChooser.app());
//        Str t = appFontChooser.title().get();
//        if(t==null){
//            t=Str.i18n("Message.Font");
//        }
//        int d = swingFontChooser.showDialog(ow, t.value(appFontChooser.app().i18n(), appFontChooser.locale().get()));
//        return d == JFontChooser.ACCEPT_OPTION;
//    }
//
//    @Override
//    public Object toolkitComponent() {
//        return swingFontChooser;
//    }
//}

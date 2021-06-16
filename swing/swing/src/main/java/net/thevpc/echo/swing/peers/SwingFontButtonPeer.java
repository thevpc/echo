//package net.thevpc.echo.swing.peers;
//
//import net.thevpc.common.i18n.Str;
//import net.thevpc.common.swing.font.JFontChooser;
//import net.thevpc.echo.api.AppFont;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppFontButton;
//import net.thevpc.echo.spi.peers.AppFontButtonPeer;
//import net.thevpc.echo.swing.SwingPeerHelper;
//import net.thevpc.echo.swing.helpers.SwingHelpers;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import net.thevpc.echo.impl.Applications;
//
//public class SwingFontButtonPeer implements AppFontButtonPeer, SwingPeer {
//
//    private JFontChooser swingFontChooser;
//    private AbstractButton swingFontButton;
//    private AppFontButton appFontChooser;
//
//    @Override
//    public void install(AppComponent comp) {
//        this.appFontChooser = (AppFontButton) comp;
//        this.swingFontChooser = new JFontChooser(null);
//        AppComponent ep = Applications.effectiveParent(comp);
//        this.swingFontButton
//                = (ep instanceof net.thevpc.echo.Menu)
//                ? new JMenuItem("Font")
//                : new JButton("Font");
//        this.swingFontButton.setIcon(
//                SwingHelpers.toAwtIcon(
//                        appFontChooser.app().iconSets().icon("font-type", appFontChooser)
//                )
//        );
//        SwingPeerHelper.installComponent(appFontChooser, swingFontButton);
//
//        this.appFontChooser.selection().onChangeAndInit(
//                () -> {
//                    AppFont f = this.appFontChooser.selection().get();
//                    this.swingFontButton.setText(
//                            f == null ? Applications.rawString(Str.i18n("FontChooser.defaultFont"), comp) : f.family()
//                    );
//                    if (f == null) {
//                        AppComponent ep2 = Applications.effectiveParent(comp);
//                        if (ep2 instanceof net.thevpc.echo.Menu) {
//                            this.swingFontButton.setFont(
//                                    new JMenuItem().getFont()
//                            );
//
//                        } else {
//                            this.swingFontButton.setFont(
//                                    new JButton().getFont()
//                                            .deriveFont(10f)
//                            );
//                        }
//                    } else {
//                        AppComponent ep2 = Applications.effectiveParent(comp);
//                        if (ep2 instanceof net.thevpc.echo.Menu) {
//                            this.swingFontButton.setFont(
//                                    new JMenuItem().getFont()
//                            );
//
//                        } else {
//                            this.swingFontButton.setFont(
//                                    SwingHelpers.toAwtFont(f).deriveFont(new JMenuItem().getFont().getSize())
//                            );
//                        }
//                    }
//                }
//        );
//        this.swingFontButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showDialog(appFontChooser);
//            }
//        });
////        swingFontChooser.addPropertyChangeListener("selectedFont", new PropertyChangeListener() {
////            @Override
////            public void propertyChange(PropertyChangeEvent evt) {
////                appFontChooser.selection().set(
////                        SwingHelpers.fromAwtFont(swingFontChooser.getSelectedFont(), appFontChooser.app())
////                );
////            }
////        });
//    }
//
//    @Override
//    public boolean showDialog(AppComponent owner) {
//        swingFontChooser.setSelectedFont(
//                SwingHelpers.toAwtFont(appFontChooser.selection().get())
//        );
//        Component ow = SwingPeerHelper.resolveOwnerComponent(owner, appFontChooser.app());
//        Str t = appFontChooser.title().get();
//        if (t == null) {
//            t = Str.i18n("Message.Font");
//        }
//        int d = swingFontChooser.showDialog(ow, t.value(appFontChooser.app().i18n(), appFontChooser.locale().get()));
//        if (d == JFontChooser.ACCEPT_OPTION) {
//            appFontChooser.selection().set(
//                    SwingHelpers.fromAwtFont(swingFontChooser.getSelectedFont(), appFontChooser.app())
//            );
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public Object toolkitComponent() {
//        return swingFontButton;
//    }
//}

//package net.thevpc.echo.swing.peers;
//
//import net.thevpc.common.swing.button.JDropDownButton;
//import net.thevpc.common.swing.label.JDropDownLabel;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppContainer;
//import net.thevpc.echo.api.components.AppPanel;
//import net.thevpc.echo.api.components.AppWindowContainer;
//import net.thevpc.echo.api.tools.AppComponentModel;
//import net.thevpc.echo.api.tools.AppTextModel;
//import net.thevpc.echo.impl.components.AppComponentBase;
//import net.thevpc.echo.swing.SwingApplicationUtils;
//import net.thevpc.echo.swing.icons.SwingAppImage;
//import net.thevpc.echo.swing.raw.JToolBarGroup;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class SwingFolderPeer implements SwingPeer {
//    private Object jcomponent;
//    private AppComponent component;
//
//    public SwingFolderPeer() {
//    }
//
//    public void install(AppComponent component) {
//        AppComponentBase ecomp = (AppComponentBase) component;
//        AppComponentModel etool = ecomp.tool();
//        Object sParent = component.parent() == null ? null : component.parent().peer().toolkitComponent();
//        if (
//                sParent instanceof JMenu
//                        || sParent instanceof JPopupMenu
//                        || sParent instanceof JDropDownButton
//                        || sParent instanceof JDropDownLabel
//        ) {
//            if (jcomponent == null) {
//                this.jcomponent = new JMenuItem();
//                JMenuItem mLabel = (JMenuItem) this.jcomponent;
//                etool.title().listeners().add(x -> mLabel.setText(
//                        etool.title().get() == null ? "" :
//                                etool.title().get().getValue(etool.app().i18n())
//                ));
//                etool.smallIcon().listeners().add(x -> mLabel.setIcon(
//                        SwingAppImage.iconOf(etool.smallIcon().get())
//                ));
//            }
//        } else if(sParent instanceof JToolBar){
//            if (jcomponent == null) {
//                JDropDownButton dd = new JDropDownButton();
//                this.jcomponent = dd;
//                dd.setQuickActionDelay(0);
//                SwingApplicationUtils.prepareAbstractButton(dd,ecomp, ecomp.app(), false);
//            }
//        } else if(sParent instanceof JToolBarGroup){
//            this.jcomponent = new JToolBar();
//        } else if(component instanceof AppPanel){
//            this.jcomponent = new JPanel();
//        } else {
//            throw new IllegalArgumentException("Unsupported folder as child of "+ecomp.getClass());
//        }
//    }
//
//    @Override
//    public Object toolkitComponent() {
//        return jcomponent;
//    }
//
//    @Override
//    public Component awtComponent() {
//        return (Component) toolkitComponent();
//    }
//
//    @Override
//    public JComponent jcomponent() {
//        return (JComponent) toolkitComponent();
//    }
//}

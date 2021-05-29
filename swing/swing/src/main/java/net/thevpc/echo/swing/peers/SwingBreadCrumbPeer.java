package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppBreadCrumb;
import net.thevpc.echo.api.components.AppChoiceControl;
import net.thevpc.echo.api.components.AppChoiceItemRenderer;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppBreadCrumbPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingAppChoiceItemContext1;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.SeparatorUI;
import javax.swing.plaf.basic.BasicSeparatorUI;
import javax.swing.plaf.metal.MetalSeparatorUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingBreadCrumbPeer implements SwingPeer, AppBreadCrumbPeer {

    ActionListenerImpl actionListenerImpl = new ActionListenerImpl();
    private AppBreadCrumb appBreadCrumb;
    private JToolBar jcomponent;

    @Override
    public void install(AppComponent comp) {
        this.appBreadCrumb = (AppBreadCrumb) comp;
        jcomponent = new JToolBar();
        jcomponent.setFloatable(false);
        SwingPeerHelper.installComponent(comp, jcomponent);
        this.appBreadCrumb.values().onChangeAndInit(() -> updateContent());
    }

    @Override
    public void uninstall() {

    }

    @Override
    public Object toolkitComponent() {
        return jcomponent;
    }

    private void updateContent() {
        while (appBreadCrumb.values().size() < jcomponent.getComponentCount() / 2) {
            JButton b = (JButton) jcomponent.getComponent(jcomponent.getComponentCount() - 1);
            b.removeActionListener(actionListenerImpl);
            jcomponent.remove(jcomponent.getComponentCount() - 1);
            jcomponent.remove(jcomponent.getComponentCount() - 1);
        }
        AppChoiceItemRenderer rend = (AppChoiceItemRenderer) appBreadCrumb.itemRenderer().get();
        for (int i = 0; i < jcomponent.getComponentCount() / 2; i++) {
            JButton b = (JButton) jcomponent.getComponent(i * 2 + 1);
            Object value = appBreadCrumb.values().get(i);
            if (rend == null) {
                b.setText(value == null ? "" : value.toString());
            } else {
                SwingAppChoiceItemContext1Impl r = new SwingAppChoiceItemContext1Impl(appBreadCrumb, b, value, i, null, false, false, false);
                rend.render(r);
            }

        }
        while (appBreadCrumb.values().size() > jcomponent.getComponentCount() / 2) {
            int index = jcomponent.getComponentCount() / 2;
            jcomponent.add(new BreadCrumbSeparator());
            JButton b = new JButton();
            Object value = appBreadCrumb.values().get(index);
            if (rend == null) {
                b.setText(value == null ? "" : value.toString());
            } else {
                SwingAppChoiceItemContext1Impl r = new SwingAppChoiceItemContext1Impl(appBreadCrumb, b, value, index, null, false, false, false);
                rend.render(r);
            }
            b.addActionListener(actionListenerImpl);
            jcomponent.add(b);
        }
    }

//    public void addChild0(AppComponent other, int index) {
//        Object o = other.peer().toolkitComponent();
//        jcomponent.add(new BreadCrumbSeparator(), 2 * index);
//        jcomponent.add((Component) o, 2 * index + 1);
//        SwingHelpers.refreshPanel(jcomponent, 2);
//    }
//
//    public void removeChild0(AppComponent other, int index) {
//        Object o = other.peer().toolkitComponent();
//        jcomponent.remove(2 * index);
//        jcomponent.remove(2 * index + 1);
//        SwingHelpers.refreshPanel(jcomponent, 2);
//    }

//    private static class BreadCrumbSeparatorUI extends MetalSeparatorUI {
//        public void paint(Graphics g, JComponent c) {
//            Dimension s = c.getSize();
//
//            if (((JSeparator) c).getOrientation() == JSeparator.VERTICAL) {
//                int w = s.width;
//                g.setColor(c.getForeground());
//                g.drawLine(0, 0, w, s.height / 2);
//                g.drawLine(w, s.height / 2, 0, s.height);
//
//                g.setColor(c.getBackground());
////          g.drawLine( 1, 0, 1, s.height );
//                g.drawLine(1, 1, w + 1, s.height / 2);
//                g.drawLine(w + 1, s.height / 2, 0, s.height);
//            } else  // HORIZONTAL
//            {
//                g.setColor(c.getForeground());
//                g.drawLine(0, 0, s.width, 0);
//
//                g.setColor(c.getBackground());
//                g.drawLine(0, 1, s.width, 1);
//            }
//        }
//
//        public Dimension getPreferredSize(JComponent c) {
//            if (((JSeparator) c).getOrientation() == JSeparator.VERTICAL)
//                return new Dimension(2, 0);
//            else
//                return new Dimension(0, 2);
//        }
//
//        @Override
//        public Dimension getMinimumSize(JComponent c) {
//            if (((JSeparator) c).getOrientation() == JSeparator.VERTICAL)
//                return new Dimension(2, 0);
//            else
//                return new Dimension(0, 2);
//        }
//    }

    private static class BreadCrumbSeparator extends JToolBar.Separator {

//        private BreadCrumbSeparatorUI sui = new BreadCrumbSeparatorUI();

//        @Override
//        protected void setUI(ComponentUI newUI) {
//            super.setUI(getSui());
//        }
//        @Override
//        public void setUI(SeparatorUI ui) {
//            super.setUI(getSui());
//        }
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
//        }


//
//        private BreadCrumbSeparatorUI getSui() {
//            if (sui == null) {
//                sui = new BreadCrumbSeparatorUI();
//            }
//            return sui;
//        }


    }

    private static class SwingAppChoiceItemContext1Impl extends SwingAppChoiceItemContext1 {

        public SwingAppChoiceItemContext1Impl(AppChoiceControl appChoiceControl, JButton jcomponent, Object value, int index, Icon icon, boolean isSelected, boolean cellHasFocus, boolean disabled) {
            super(appChoiceControl, jcomponent, value, index, icon, isSelected, cellHasFocus, disabled);
        }

        @Override
        public void renderDefault() {

        }

    }

    private class ActionListenerImpl implements ActionListener {

        public ActionListenerImpl() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int c = jcomponent.getComponentCount();
            for (int i = 0; i < c / 2; i++) {
                JButton b = (JButton) jcomponent.getComponent(2 * i + 1);
                if (b == e.getSource()) {
                    appBreadCrumb.selection().set(appBreadCrumb.values().get(i));
                }

            }
        }
    }

}

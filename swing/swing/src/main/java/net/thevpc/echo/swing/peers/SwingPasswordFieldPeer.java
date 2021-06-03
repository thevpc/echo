package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppPasswordField;
import net.thevpc.echo.spi.peers.AppPasswordFieldPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class SwingPasswordFieldPeer implements SwingPeer, AppPasswordFieldPeer {
    private MyJPasswordField jTextField;
    private AppPasswordField appTextField;

    public SwingPasswordFieldPeer() {
    }

    public void install(AppComponent component) {
        appTextField = (AppPasswordField) component;
        jTextField = new MyJPasswordField();

        SwingPeerHelper.installComponent(appTextField, this.jTextField);
        new SwingPeerHelper.InstallTextComponent(appTextField, this.jTextField, appTextField.text()).install();
    }

    @Override
    public Object toolkitComponent() {
        return jTextField;
    }

    private class MyJPasswordField extends JPasswordField {
        public MyJPasswordField() {
            addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1
                            && isOverLockImage(e.getX(), e.getY())) {
                        setPasswordVisible(!isPasswordVisible());
                    }
                }
            });
            addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {

                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    if(isOverLockImage(e.getX(), e.getY())){
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }else{
                        setCursor(new Cursor(Cursor.TEXT_CURSOR));
                    }
                }
            });
        }

        private boolean isOverLockImage(int x, int y) {
            Dimension s = getSize();
            int x0 = getIconXmin(s);
            int y0 = getIconYMin(s);
            if (x >= x0 && x <= x0 + getImageSize()) {
                if (y >= y0 && y <= y0 + getImageSize()) {
                    return true;
                }
            }
            return false;
        }

        public boolean isPasswordVisible() {
            return getEchoChar() == '\0';
        }

        public void setPasswordVisible(boolean b) {
            setEchoChar(b ? '\0' : '*');
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            AppImage lock = appTextField.app().iconSets().icon(
                    isPasswordVisible() ?
                            "unlock" : "lock", appTextField);
            Image image = SwingHelpers.toAwtImage(lock.scaleTo(getImageSize(), getImageSize()));
            Dimension s = getSize();
            g.drawImage(image, getIconXmin(s), getIconYMin(s), jTextField);
        }

        private int getIconYMin(Dimension s) {
            return s.height / 2 - getImageSize() / 2;
        }

        private int getIconXmin(Dimension s) {
            return s.width - getImageSize() - 3;
        }

        private int getImageSize() {
            Dimension s = getSize();
            return s.height - 6;
        }

    }

}

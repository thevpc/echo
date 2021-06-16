/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.thevpc.common.swing.icon.EmptyIcon;

/**
 *
 * @author vpc
 */
public class SwingAlertHeader extends Box {

    JLabel hh = new JLabel();

    public SwingAlertHeader() {
        super(BoxLayout.X_AXIS);
        hh.setHorizontalTextPosition(SwingConstants.RIGHT);
        hh.setIconTextGap(16);
        hh.setOpaque(true);
        hh.setPreferredSize(new Dimension(400, 60));
        hh.setMinimumSize(new Dimension(400, 60));
        hh.setFont(hh.getFont().deriveFont(Font.BOLD, (int) (hh.getFont().getSize() * 1.2)));
        hh.setOpaque(false);
        this.add(Box.createHorizontalStrut(10));
        this.add(hh);

        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
        setHeaderIcon(null);
        setHeaderText(null);
    }

    public void setHeaderText(String text) {
        hh.setText(text);
    }

    public void setHeaderIcon(Icon icon) {
        if (icon == null) {
            icon = new EmptyIcon(24, 24);
        }
        hh.setIcon(icon);
    }

}

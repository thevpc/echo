/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.impl.tools.ToolWindow;
import net.thevpc.echo.swing.icons.IconUtils;
import net.thevpc.echo.swing.peers.SwingPeer;
import org.noos.xing.mydoggy.Dockable;

import java.awt.*;

/**
 *
 * @author thevpc
 */
public class MyDoggyAbstractWindow  {




    public static Icon resizeIcon(Icon icon) {
        if (icon == null) {
            return icon;
        }
        if (icon instanceof ImageIcon) {
            return new ImageIcon(IconUtils.getFixedSizeImage(((ImageIcon) icon).getImage(), 8, 8));
        } else {
            return resizeIcon(
                    new ImageIcon(
                            IconUtils.iconToImage(icon)
                    )
            );
        }
    }

}
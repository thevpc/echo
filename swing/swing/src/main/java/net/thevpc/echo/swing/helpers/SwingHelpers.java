package net.thevpc.echo.swing.helpers;

import net.thevpc.echo.Application;
import net.thevpc.echo.Dimension;
import net.thevpc.echo.FontPosture;
import net.thevpc.echo.FontWeight;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;

import javax.swing.*;
import java.awt.*;

public class SwingHelpers {
    public static Icon toAwtIcon(AppImage i) {
        if(i==null){
            return null;
        }
        return (Icon) i.peer().toolkitImage();
    }
    public static Image toAwtImage(AppImage i) {
        if(i==null){
            return null;
        }
        ImageIcon o = (ImageIcon) i.peer().toolkitImage();
        return o==null?null:o.getImage();
    }

    public static java.awt.Dimension toAwtDimension(Dimension d) {
        if(d==null){
            return null;
        }
        return new java.awt.Dimension((int) d.getWidth(),(int) d.getHeight());
    }

    public static void refreshPanel(Component c, int lvl) {
        Component p = c;
        for (int i = 0; i < lvl; i++) {
            if (p != null) {
                p.invalidate();
                p.revalidate();
                p.repaint();
                p = p.getParent();
            } else {
                break;
            }
        }
    }

    public static Color toAwtColor(AppColor color) {
        return color==null?null:(Color) color.peer().toolkitColor();
    }

    public static Font toAwtFont(AppFont color) {
        return color==null?null:(Font) color.peer().toolkitFont();
    }


    public static AppColor fromAwtColor(Color newValue, Application app) {
        return newValue==null?null:new net.thevpc.echo.Color(
                newValue.getRGB(),true,app
        );
    }

    public static AppFont fromAwtFont(Font f, Application app){
        if(f==null){
            return null;
        }
        return new net.thevpc.echo.Font(
                f.getFamily(),
                f.getSize(),
                f.isBold()? FontWeight.BOLD:FontWeight.NORMAL,
                f.isItalic()? FontPosture.ITALIC:FontPosture.REGULAR,
                app
        );
    }
}

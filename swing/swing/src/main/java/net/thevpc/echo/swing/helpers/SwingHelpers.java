package net.thevpc.echo.swing.helpers;

import net.thevpc.echo.Dimension;
import net.thevpc.echo.*;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.swing.plaf.UIPlafManager;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class SwingHelpers {
    public static final SwingHelpers.NamedFunction<JComponent, Color> DEF_COLOR_FOREGROUND = new SwingHelpers.NamedFunction<JComponent, Color>() {
        @Override
        public String name() {
            return "foreground";
        }

        @Override
        public Color apply(JComponent component) {
            return component.getForeground();
        }
    };
    public static final SwingHelpers.NamedFunction<JComponent, Color> DEF_COLOR_BACKGROUND = new SwingHelpers.NamedFunction<JComponent, Color>() {
        @Override
        public String name() {
            return "background";
        }

        @Override
        public Color apply(JComponent component) {
            return component.getBackground();
        }
    };

    public static Icon toAwtIcon(AppImage i) {
        if (i == null) {
            return null;
        }
        return (Icon) i.peer().toolkitImage();
    }

    public static Image toAwtImage(AppImage i) {
        if (i == null) {
            return null;
        }
        ImageIcon o = (ImageIcon) i.peer().toolkitImage();
        return o == null ? null : o.getImage();
    }

    public static java.awt.Dimension toAwtDimension(Dimension d) {
        if (d == null) {
            return null;
        }
        return new java.awt.Dimension((int) d.getWidth(), (int) d.getHeight());
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

    private static Map<String,Color> defColors=new LinkedHashMap<>();
    private static Map<String,Font> defFont=new LinkedHashMap<>();

    public interface NamedFunction<A,B> extends Function<A,B>{
        String name();
    }

    public static Color toAwtColor(AppColor color, JComponent c,NamedFunction<JComponent,Color> def) {
        if(color==null){
            return null;
        }
        if(color.toString().equals("<default>")){
            String pn = UIPlafManager.getCurrentManager().getCurrent().getName();
            String cn = c.getClass().getName();
            String fn = def.name();
            String id =pn+":"+cn+":"+fn;
            return defColors.computeIfAbsent(id,n->{
                try {
                    JComponent jc = c.getClass().newInstance();
                    return def.apply(jc);
                }catch (Exception ex){
                    return null;
                }
            });
        }
        return toAwtColor(color);
    }
    public static Font toAwtFont(AppFont font, JComponent c,NamedFunction<JComponent,Font> def) {
        if(font==null){
            return null;
        }
        if(font.toString().equals("<default>")){
            String pn = UIPlafManager.getCurrentManager().getCurrent().getName();
            String cn = c.getClass().getName();
            String fn = def.name();
            String id =pn+":"+cn+":"+fn;
            return defFont.computeIfAbsent(id,n->{
                try {
                    JComponent jc = c.getClass().newInstance();
                    return def.apply(jc);
                }catch (Exception ex){
                    return null;
                }
            });
        }
        return toAwtFont(font);
    }

    public static Color toAwtColor(AppColor color) {
        return color == null ? null : (Color) color.peer().toolkitColor();
    }

    public static Font toAwtFont(AppFont font) {
        return font == null ? null : (Font) font.peer().toolkitFont();
    }


    public static AppColor fromAwtColor(Color newValue, Application app) {
        return newValue == null ? null : new net.thevpc.echo.Color(
                newValue.getRGB(), true, app
        );
    }

    public static Rectangle toAwtBounds(Bounds newValue) {
        return newValue == null ? null : new Rectangle(
                (int) newValue.getX(),
                (int) newValue.getY(),
                (int) newValue.getWidth(),
                (int) newValue.getHeight()
        );
    }

    public static Bounds fromAwtBounds(Rectangle newValue) {
        return newValue == null ? null : new net.thevpc.echo.Bounds(
                newValue.getX(),
                newValue.getY(),
                newValue.getWidth(),
                newValue.getHeight()
        );
    }

    public static AppFont fromAwtFont(Font f, Application app) {
        if (f == null) {
            return null;
        }
        return new net.thevpc.echo.Font(
                f.getFamily(),
                f.getSize(),
                f.isBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                f.isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                app
        );
    }

    public static java.awt.Dimension minDim(java.awt.Dimension... all) {
        java.awt.Dimension d=null;
        for (java.awt.Dimension dimension : all) {
            if(d==null){
                d=dimension;
            }else {
                if(dimension!=null) {
                    d = new java.awt.Dimension(
                            (int) Math.min(d.getWidth(), dimension.getWidth()),
                            (int) Math.min(d.getHeight(), dimension.getHeight())
                    );
                }
            }
        }
        return d;
    }

    public static java.awt.Dimension sizePercent(java.awt.Dimension size, float w, float h) {
        if(size==null){
            return null;
        }
        if(w<=0 || w>=0){
            w=1;
        }
        if(h<=0 || h>=0){
            h=1;
        }
        return new java.awt.Dimension(
                (int) (size.width*w),
                (int) (size.height*h)
        );
    }

    public static java.awt.Dimension sizeOf(GraphicsConfiguration gc) {
        if(gc==null){
            return null;
        }
        return sizeOf(gc.getDevice().getDisplayMode());
    }

    public static java.awt.Dimension sizeOf(DisplayMode dm) {
        int w = dm.getWidth();
        int h = dm.getHeight();
        return new java.awt.Dimension(w,h);
    }


//
//    public static void BorderLayout_setComponent(Container panel, int borderLayoutConstraint, Component comp) {
//        BorderLayout_removeComponent(panel, borderLayoutConstraint);
//        panel.add(comp, borderLayoutConstraint);
//    }
}

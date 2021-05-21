/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.*;

/**
 *
 * @author vpc
 */
public class ColorUtils {

    private static final Random RANDOM = new Random();
    public static final String TEXTUTE_ID_SAMPLE1 = "sample-1";
    public static final String TEXTUTE_ID_SAMPLE2 = "sample-2";
    public static final String TEXTUTE_ID_SAMPLE3 = "sample-3";
    public static final String TEXTUTE_ID_SAMPLE4 = "sample-4";
    public static final String TEXTUTE_ID_SAMPLE5 = "Board";
//    public static final String TEXTUTE_ID_SAMPLE6="sample-6";

    public static String formatPaint(Paint s) {
        if (s instanceof GradientPaint) {
            GradientPaint g = (GradientPaint) s;
            return "gradient:" + g.getPoint1().getX() + "," + g.getPoint1().getY() + "," + formatColor(g.getColor1());
        }
        if (s instanceof TexturePaint) {
            TexturePaint g = (TexturePaint) s;
            BufferedImage ii = g.getImage();
            if (ii instanceof BufferedImageExt) {
                Map<String, String> info = ((BufferedImageExt) ii).getInfo();
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> ee : info.entrySet()) {
                    if (sb.length() > 0) {
                        sb.append(";");
                    }
                    sb.append(ee.getKey()).append("=").append(ee.getValue());
                }
                return "texture:" + sb;
            }
        }
        if (s instanceof Color) {
            return formatColor((Color) s);
        }
        return "";
    }

    public static Color parseColor(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        if (s.indexOf(",") > 0) {
            String r = null;
            String g = null;
            String b = null;
            String[] sp = s.split(",");
            if (sp.length == 3) {
                try {
                    return new Color(Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b));
                } catch (Exception ex) {
                }
            }
            return null;
        } else if (s.matches("#[a-fA-F0-9]{12}")) {
            try {
                return new Color(
                        Integer.parseInt(s.substring(1, 5), 16),
                        Integer.parseInt(s.substring(5, 9), 16),
                        Integer.parseInt(s.substring(9, 13), 16)
                );
            } catch (Exception ex) {
                //
            }
        } else if (s.matches("#[a-fA-F0-9]{16}")) {
            try {
                return new Color(
                        Integer.parseInt(s.substring(1, 5), 16),
                        Integer.parseInt(s.substring(5, 9), 16),
                        Integer.parseInt(s.substring(9, 13), 16),
                        Integer.parseInt(s.substring(13, 17), 16)
                );
            } catch (Exception ex) {
                //
            }
        } else if (s.matches("[a-fA-F0-9]{12}")) {
            try {
                return new Color(Integer.parseInt(s.substring(0, 4), 16), Integer.parseInt(s.substring(4, 8), 16), Integer.parseInt(s.substring(8, 12), 16));
            } catch (Exception ex) {
                //
            }
        } else if (s.matches("[a-fA-F0-9]{16}")) {
            try {
                return new Color(
                        Integer.parseInt(s.substring(0, 4), 16),
                        Integer.parseInt(s.substring(4, 8), 16),
                        Integer.parseInt(s.substring(8, 12), 16),
                        Integer.parseInt(s.substring(12, 16), 16)
                );
            } catch (Exception ex) {
                //
            }
        } else {
            switch (s.toLowerCase()) {
                case "black": {
                    return Color.BLACK;
                }
                case "white": {
                    return Color.WHITE;
                }
                case "blue": {
                    return Color.BLUE;
                }
                case "red": {
                    return Color.RED;
                }
                case "yellow": {
                    return Color.YELLOW;
                }
                case "darkgray": {
                    return Color.DARK_GRAY;
                }
                case "lightgray": {
                    return Color.LIGHT_GRAY;
                }
                case "gray": {
                    return Color.GRAY;
                }
                case "cyan": {
                    return Color.cyan;
                }
                case "orange": {
                    return Color.ORANGE;
                }
                case "magenta": {
                    return Color.MAGENTA;
                }
                case "green": {
                    return Color.GREEN;
                }
                case "pink": {
                    return Color.PINK;
                }
                case "random": {
                    float r = RANDOM.nextFloat();
                    float g = RANDOM.nextFloat();
                    float b = RANDOM.nextFloat();
                    return new Color(r, g, b);
                }

            }
        }
        return null;
    }

    public static String formatColor(Color s) {
        if (s == null) {
            return "";
        }
        return "#"
                + toHex(s.getRed(), 4)
                + toHex(s.getGreen(), 4)
                + toHex(s.getBlue(), 4)
                + toHex(s.getAlpha(), 4);
    }

    private static String toHex(int value, int pad) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(value));
        while (sb.length() < pad) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static Paint parsePaint(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        if (s.startsWith("gradient:")) {
            try {
                s = s.substring("gradient:".length());
                String[] a = s.split("[,;]");
                float x1 = Float.parseFloat(a[0].trim());
                float y1 = Float.parseFloat(a[1].trim());
                Color c1 = parseColor(a[2].trim());
                float x2 = Float.parseFloat(a[0].trim());
                float y2 = Float.parseFloat(a[1].trim());
                Color c2 = parseColor(a[2].trim());
                return new GradientPaint(x1, y1, c1, x2, y2, c2);
            } catch (Exception ex) {
                return null;
            }
        } else if (s.startsWith("texture:")) {
            try {
                s = s.substring("texture:".length());
                String[] a = s.split("[,;]");
                Map<String, String> info = new HashMap<>();
                for (String string : a) {
                    String[] q = string.split("=");
                    if (q.length == 1 && info.isEmpty()) {
                        info.put("type", q[0].trim());
                    } else if (q.length == 1) {
                        info.put(q[0].trim(), q[0].trim());
                    } else {
                        info.put(q[0].trim(), q[1].trim());
                    }
                }
//                System.out.println(info);
                String type = info.get("type");
                if (type == null) {
                    type = "";
                }
                switch (type) {
                    case TEXTUTE_ID_SAMPLE1:
                    case TEXTUTE_ID_SAMPLE2:
                    case TEXTUTE_ID_SAMPLE3:
                    case TEXTUTE_ID_SAMPLE4: {
                        int width = _getInt("width", 8, info);
                        int height = _getInt("height", 8, info);
                        Color color = _getColor("color", Color.BLACK, info);
                        return new TexturePaint(
                                getTextureVertical(type, width, height, color),
                                new Rectangle2D.Double(0, 0, width, height));
                    }
                    case TEXTUTE_ID_SAMPLE5: {
                        return getCheckerBoard(
                                _getInt("size", 4, info),
                                _getColor("color", Color.BLACK, info),
                                _getColor("color2", Color.WHITE, info)
                        );
                    }
                }
                return null;
            } catch (Exception ex) {
                return null;
            }
        } else {
            return parseColor(s);
        }
    }

    private static String _getString(String s, String def, Map<String, ?> info) {
        Object a = info.get(s);
        if (a == null) {
            return def;
        }
        return String.valueOf(a);
    }

    private static int _getInt(String s, int def, Map<String, ?> info) {
        Object a = info.get(s);
        if (a == null) {
            return def;
        }
        if (a instanceof Number) {
            return ((Number) a).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(a));
        } catch (Exception e) {
            return def;
        }
    }

    private static Color _getColor(String s, Color def, Map<String, ?> info) {
        Object a = info.get(s);
        if (a == null) {
            return def;
        }
        if (a instanceof Color) {
            return ((Color) a);
        }
        try {
            return parseColor(String.valueOf(a));
        } catch (Exception e) {
            return def;
        }
    }

    private static Paint _getPaint(String s, Color def, Map<String, ?> info) {
        Object a = info.get(s);
        if (a == null) {
            return def;
        }
        if (a instanceof Paint) {
            return ((Paint) a);
        }
        try {
            return parsePaint(String.valueOf(a));
        } catch (Exception e) {
            return def;
        }
    }

    public static TexturePaint getCheckerBoard(
            int checkerSize, Color color1,
            Color color2) {
        Map<String, String> info = new HashMap<>();
        info.put("type", "Board");
        info.put("color", formatColor(color1));
        info.put("color2", formatColor(color2));
        info.put("size", "" + checkerSize);
        BufferedImageExt bi = new BufferedImageExt(info, 2 * checkerSize, 2 * checkerSize,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(color1);
        g.fillRect(0, 0, 2 * checkerSize, 2 * checkerSize);
        g.setColor(color2);
        g.fillRect(0, 0, checkerSize, checkerSize);
        g.fillRect(checkerSize, checkerSize, checkerSize, checkerSize);
        g.dispose();
        return new TexturePaint(bi,
                new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
    }

    private static class BufferedImageExt extends BufferedImage {

        private Map<String, String> info;

        public BufferedImageExt(Map<String, String> info, int i, int i1, int i2) {
            super(i, i1, i2);
            this.info = info;
        }

        public BufferedImageExt(Map<String, String> info, int i, int i1, int i2, IndexColorModel icm) {
            super(i, i1, i2, icm);
            this.info = info;
        }

        public BufferedImageExt(Map<String, String> info, ColorModel cm, WritableRaster wr, boolean bln, Hashtable<?, ?> hshtbl) {
            super(cm, wr, bln, hshtbl);
            this.info = info;
        }

        public Map<String, String> getInfo() {
            return info;
        }
    }

    private static BufferedImage getTextureVertical(String id, int w, int h, Color color) {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("type", id);
        info.put("color", formatColor(color));
        info.put("width", "" + w);
        info.put("height", "" + h);
        BufferedImageExt bi = new BufferedImageExt(info, w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setColor(color);
//        System.out.println("==> " + id + " " + info + " " + color);
        switch (id) {
            case TEXTUTE_ID_SAMPLE1: {
//                System.out.println("==> sample 1" + info + " with color " + color);
                g.drawLine(0, 0, 0, h);
                break;
            }
            case TEXTUTE_ID_SAMPLE2: {
                g.drawLine(0, 0, w, 0);
                break;
            }
            case TEXTUTE_ID_SAMPLE3: {
                g.drawLine(0, 0, w, h);
                break;
            }
            case TEXTUTE_ID_SAMPLE4: {
                g.drawLine(0, h, w, 0);
                break;
            }
        }
        g.dispose();
        return bi;
    }

//    private static BufferedImage getTextureOb1(String id, int w, int h, Color color) {
//        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.getGraphics();
//        g.setColor(color);
////        switch (id) {
////            case "sample-1": {
//        // TODO: something more interesting here.. 
//        g.drawLine(0, 0, w, h);
////                break;
////            }
////        }
//        g.dispose();
//        return bi;
//    }
//    private static BufferedImage getTextureOb2(String id, int w, int h, Color color) {
//        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.getGraphics();
//        g.setColor(color);
////        switch (id) {
////            case "sample-1": {
//        // TODO: something more interesting here.. 
//        g.drawLine(0, h, w, 0);
////                break;
////            }
////        }
//        g.dispose();
//        return bi;
//    }
//    private static BufferedImage getTextureOb3(String id, int w, int h, Color color) {
//        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.getGraphics();
//        g.setColor(color);
////        switch (id) {
////            case "sample-1": {
//        // TODO: something more interesting here.. 
//        g.drawLine(w, 0, 0, h);
////                break;
////            }
////        }
//        g.dispose();
//        return bi;
//    }
//    private static BufferedImage getTextureHorizontal(String id, int w, int h, Color color) {
//        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.getGraphics();
//        g.setColor(color);
////        switch (id) {
////            case "sample-1": {
//        // TODO: something more interesting here.. 
//        g.drawLine(0, 0, w, 0);
////                g.drawLine(0, h/2, w, h/2);
////                g.drawLine(0, h, w, h);
////                break;
////            }
////        }
//        g.dispose();
//        return bi;
//    }
    // adds alpha to a color
    static public Color changeAlpha(Color c, int alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }

    // blends two colors... this is NOT symmetric
    static public Color blendColors(Paint c1, Color c2, double amt) {
        if (c1 instanceof Color) {
            return blendColors((Color) c1, c2, amt);
        }
        return c2;
    }

    // blends two colors... this is NOT symmetric
    static public Color blendColors(Color c1, Paint c2, double amt) {
        if (c2 instanceof Color) {
            return blendColors(c1, (Color) c2, amt);
        }
        return c1;
    }

    /**
     * Returns a foreground color (for text) given a background color by
     * examining the brightness of the background color.
     *
     * @param color the foreground color
     * @return Color
     */
    public static final Color getForegroundColorFromBackgroundColor(Paint color) {
        int brightness = getBrightness(color);
        if (brightness < 130) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    static public Color blendColors(Color c1, Color c2, double amt) {
        if (c1 == null || c2 == null) {
            return null;
        }
        int red = (int) (c2.getRed() * amt + c1.getRed() * (1 - amt));
        int green = (int) (c2.getGreen() * amt + c1.getGreen() * (1 - amt));
        int blue = (int) (c2.getBlue() * amt + c1.getBlue() * (1 - amt));
        int alpha = c1.getAlpha();    // keep first color's alpha
        return new Color(red, green, blue, alpha);
    }

    // blends two colors... this is NOT symmetric
    static public Color addColors(Color c1, Color c2, double amt) {
        if (c1 == null || c2 == null) {
            return null;
        }
        int red = (int) (c2.getRed() * amt + c1.getRed());
        if (red > 255) {
            red = 255;
        }
        int green = (int) (c2.getGreen() * amt + c1.getGreen());
        if (green > 255) {
            green = 255;
        }
        int blue = (int) (c2.getBlue() * amt + c1.getBlue());
        if (blue > 255) {
            blue = 255;
        }
        int alpha = c1.getAlpha();    // keep first color's alpha
        return new Color(red, green, blue, alpha);
    }

    static double getBlendAmt(Double dist) {
        double blendAmt;
        double fogStart = 0, fogEnd = 2;
        blendAmt = (dist - fogStart) / (fogEnd - fogStart);
        if (blendAmt > 1) {
            return 1;
        } else if (blendAmt < 0) {
            return 0;
        } else {
            return blendAmt;
        }
    }

    /**
     * Returns an array of color components for the given Color object.
     *
     * @param color the color object
     * @return float[]
     */
    public static final float[] convertColor(Color color) {
        return color.getRGBComponents(null);
    }

    /**
     * Returns a new Color object given the color components in the given array.
     *
     * @param color the color components in RGB or RGBA
     * @return Color
     */
    public static final Color convertColor(float[] color) {
        if (color.length == 3) {
            return new Color(color[0], color[1], color[2]);
        } else if (color.length == 4) {
            return new Color(color[0], color[1], color[2], color[3]);
        } else {
            throw new IllegalArgumentException("missing arguments");
        }
    }

    /**
     * Places the RGBA values from the given Color object into the destination
     * array.
     *
     * @param color the color to convert
     * @param destination the array to hold the RGBA values; length 4
     */
    public static final void convertColor(Color color, float[] destination) {
        color.getRGBComponents(destination);
    }

    public static final int getBrightness(Paint color) {
        if (color instanceof Color) {
            return getBrightness((Color) color);
        }
        if (color instanceof GradientPaint) {
            GradientPaint p1 = (GradientPaint) color;
            return getBrightness(blendColors(p1.getColor1(), p1.getColor2(), 0.5));
        }
        return getBrightness(Color.gray);
    }

    /**
     * Uses the method described at http://alienryderflex.com/hsp.html to get
     * the <u>perceived</u> brightness of a color.
     *
     * @param color the color
     * @return int brightness on the scale of 0 to 255
     */
    public static final int getBrightness(Color color) {
        // original coefficients
        final double cr = 0.241;
        final double cg = 0.691;
        final double cb = 0.068;
        // another set of coefficients
//		final double cr = 0.299;
//		final double cg = 0.587;
//		final double cb = 0.114;

        double r, g, b;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();

        // compute the weighted distance
        double result = Math.sqrt(cr * r * r + cg * g * g + cb * b * b);

        return (int) result;
    }

    /**
     * Returns a foreground color (for text) given a background color by
     * examining the brightness of the background color.
     *
     * @param color the foreground color
     * @return Color
     */
    public static final Color getForegroundColorFromBackgroundColor(Color color) {
        int brightness = getBrightness(color);
        if (brightness < 130) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Returns a foreground color (for text) given a background color by
     * examining the brightness of the background color.
     *
     * @param color the foreground color
     * @return Color
     */
    public static final Color getInvertColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getBlue(), 255 - color.getGreen(), color.getAlpha());
    }

    /**
     * Returns a random color given the offset and alpha values.
     *
     * @param offset the offset between 0.0 and 1.0
     * @param alpha the alpha value between 0.0 and 1.0
     * @return Color
     */
    public static final Color getRandomColor(float offset, float alpha) {
        final float max = 1.0f;
        final float min = 0.0f;
        // make sure the offset is valid
        if (offset > max) {
            offset = min;
        }
        if (offset < min) {
            offset = min;
        }
        // use the offset to calculate the color
        float multiplier = max - offset;
        // compute the rgb values
        float r = (float) Math.random() * multiplier + offset;
        float g = (float) Math.random() * multiplier + offset;
        float b = (float) Math.random() * multiplier + offset;

        return new Color(r, g, b, alpha);
    }

    /**
     * Returns a new color that is darker or lighter than the given color by the
     * given factor.
     *
     * @param color the color to modify
     * @param factor 0.0 &le; factor &le; 1.0 darkens; 1.0 &lt; factor brightens
     * @return Color
     * @since 1.0.1
     */
    public static final Color getColor(Color color, float factor) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[2] = hsb[2] * factor;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] * factor));
    }

    public static Color paintToColor(Paint color) {
        if (color instanceof Color) {
            return ((Color) color);
        }
        if (color instanceof GradientPaint) {
            GradientPaint p1 = (GradientPaint) color;
            return blendColors(p1.getColor1(), p1.getColor2(), 0.5);
        }
        if (color instanceof TexturePaint) {
            TexturePaint p1 = (TexturePaint) color;
            BufferedImage i = p1.getImage();
            if (i instanceof BufferedImageExt) {
                BufferedImageExt w = (BufferedImageExt) i;
                String c = w.getInfo().get("color");
                return parseColor(c);
            }
        }
        return (Color.gray);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.icons;

import com.kitfox.svg.SVGCache;
import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author thevpc
 */
public class SVGSalamander {

    public static Image getFactorScaledSVG(URL url, float width0, float height0) {
        try {
            SVGDiagram diagram = getSvgDiagram(url);
            if(diagram.getRoot()==null){
                System.err.println("null root");
            }
            float dw = diagram.getWidth();
            float dh = diagram.getHeight();
            double wscale = 1;
            double hscale = 1;
            if (width0 <= 0 && height0 <= 0) {
                //do nothing
            } else if (width0 <= 0 && height0 > 0) {
                if (height0 != dh) {
                    hscale = height0;
                    wscale = hscale;
                    dw = (float) (dw * hscale);
                    dh = dh * height0;
                }
            } else if (width0 > 0 && height0 <= 0) {
                if (width0 != dw) {
                    wscale = width0;
                    hscale = wscale;
                    dh = (float) (dh * wscale);
                    dw = dw * width0;
                }
            } else {
                if (!(width0 == 1 && height0 == 1)) {
                    wscale = width0;
                    hscale = height0;
                    dh = dh * height0;
                    dw = dw * width0;
                }
            }

            BufferedImage image = new BufferedImage((int) dw, (int) dh, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = image.createGraphics();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                if (wscale != 1 || hscale != 1) {
                    g.scale(wscale, hscale);
                }
                diagram.setIgnoringClipHeuristic(true);
                diagram.render(g);
            } finally {
                g.dispose();
            }
            return image;

        } catch (SVGException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

//    private static final Map<URI,SVGDiagram> loaded=new HashMap<>();
//    private static final Set<URI> loading=new HashSet<>();
    private static SVGDiagram getSvgDiagram(URL url) throws URISyntaxException {
        SVGDiagram diagram=null;
        URI uri = url.toURI();
//        synchronized (loaded) {
//            SVGDiagram a = loaded.get(uri);
//            if(a!=null){
//                return a;
//            }
//        }
//        boolean stillLoading=true;
//        while(stillLoading) {
//            synchronized (loading) {
//                if (loading.contains(uri)) {
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        //
//                    }
//                }else{
//                    loading.add(uri);
//                    stillLoading=false;
//                }
//            }
//        }
        try {
            synchronized (SVGCache.getSVGUniverse()) {
                diagram = SVGCache.getSVGUniverse().getDiagram(uri);
            }
        }finally {
//            synchronized (loading) {
//                loading.remove(uri);
//            }
//            synchronized (loaded) {
//                loaded.put(uri, diagram);
//            }
        }
        return diagram;
    }

    public static Image getFixedSizeSvg(URL url, int width0, int height0) {
        try {

            SVGDiagram diagram = getSvgDiagram(url);
            if (diagram.getRoot() == null) {
                throw new IllegalArgumentException("invalid svg (null root): " + url);
            }
            float dw = diagram.getWidth();
            float dh = diagram.getHeight();
            if (dw == 0) {
                dw = 16;
            }
            if (dh == 0) {
                dh = 16;
            }
            double wscale = 1;
            double hscale = 1;
            if (width0 <= 0 && height0 <= 0) {
                //do nothing
            } else if (width0 <= 0 && height0 > 0) {
                if (height0 != dh) {
                    hscale = height0 / dh;
                    wscale = hscale;
                    dw = (float) (dw * hscale);
                    dh = height0;
                }
            } else if (width0 > 0 && height0 <= 0) {
                if (width0 != dw) {
                    wscale = width0 / dw;
                    hscale = wscale;
                    dh = (float) (dh * wscale);
                    dw = width0;
                }
            } else {
                if (width0 != dw || height0 != dh) {
                    wscale = width0 / dw;
                    hscale = height0 / dh;
                    dh = height0;
                    dw = width0;
                }
            }

            BufferedImage image = new BufferedImage((int) dw, (int) dh, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = image.createGraphics();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                if (wscale != 1 || hscale != 1) {
                    g.scale(wscale, hscale);
                }
                diagram.setIgnoringClipHeuristic(true);
                diagram.render(g);
            } finally {
                g.dispose();
            }
            return image;

        } catch (SVGException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

}

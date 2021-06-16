package net.thevpc.echo.swing.peers.htmleditor;

import net.thevpc.more.shef.WysiwygHTMLEditorKit;

import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SwingRichHtmlEditorKit extends WysiwygHTMLEditorKit {
    Map<String, File> cachedImageFiles = new HashMap<>();

    public SwingRichHtmlEditorKit() {
        wysFactory = new WysiwygHTMLFactory2();
    }

    private class ImageView2 extends ImageView {
        public ImageView2(Element elem) {
            super(elem);
        }

        @Override
        public URL getImageURL() {
            //process the case of embedded base64 image
            //example data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUA
            //    AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
            //        9TXL0Y4OHwAAAABJRU5ErkJggg==
            String src = (String) getElement().getAttributes().
                    getAttribute(HTML.Attribute.SRC);
            if (src == null) {
                return null;
            }
            if (src.startsWith("data:image")) {
                try {
                    int a = src.lastIndexOf(";");
                    int b = src.lastIndexOf(",");
                    a=Math.max(a,b);
                    String b64 = src.substring(a + 1)
                            .replaceAll("[ \n\r]","")
                            ;
                    File f = cachedImageFiles.get(b64);
                    if (f == null) {
                        byte[] bytes = Base64.getDecoder().decode(b64);
                        f = File.createTempFile("img", ".png");
                        Files.write(f.toPath(), bytes);
                        cachedImageFiles.put(b64, f);
                    }
                    return f.toURI().toURL();
                } catch (Exception ex) {
                    //
                }
            }

            URL u = super.getImageURL();
            return u;
        }
    }

    private class WysiwygHTMLFactory2 extends WysiwygHTMLFactory {
        @Override
        protected View createBase(Element elem) {
            AttributeSet attrs = elem.getAttributes();
            Object elementName =
                    attrs.getAttribute(AbstractDocument.ElementNameAttribute);
            Object o = (elementName != null) ?
                    null : attrs.getAttribute(StyleConstants.NameAttribute);
            if (o instanceof HTML.Tag) {
                HTML.Tag kind = (HTML.Tag) o;
                if (kind == HTML.Tag.IMG) {
                    return new ImageView2(elem);
                }
            }
            return super.createBase(elem);
        }
    }
}

package net.thevpc.echo.impl;

import net.thevpc.common.i18n.I18n;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.PropertyUpdate;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.AppContentAdapter;
import net.thevpc.echo.AppState;
import net.thevpc.echo.Application;
import net.thevpc.echo.Image;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.iconset.IconConfig;
import net.thevpc.echo.impl.components.ComponentBase;
import net.thevpc.echo.spi.peers.AppPanelPeer;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

public class Applications {

    private static Properties EXT_TO_MIMETYPE;

    private Applications() {
    }

    public static String rawString(WritableStr str, AppComponent comp) {
        return rawString(str.get(), comp);
    }

    public static String rawString(Str str, AppComponent comp) {
        return rawString(str, comp.app(), comp.locale().get());
    }

    public static String rawString(Str str, Application app, Locale locale) {
        return rawString(str, app.i18n(), locale);
    }

    public static String rawString(Str str, I18n i18n, Locale locale) {
        if (str == null) {
            return "";
        }
        return str.value(i18n, locale);
    }

    public static WritableStr resolveTextProperty(AppComponent model) {
        WritableStr textStr = null;
        if (model instanceof AppTextControl) {
            textStr = ((AppTextControl) model).text();
        }
        return textStr;
    }

    public static String debugString(Object o) {
        if (o instanceof AbstractButton) {
            return ((AbstractButton) o).getText();
        }
        if (o instanceof AppComponent) {
            AppComponent a = (AppComponent) o;
            if (a instanceof AppTextControl) {
                return "path(" + a.path().get() + ") : text(" + ((AppTextControl) a).text().get() + ")";
            }
            return a.path().get().toString();
        }
        return o.toString();
    }

    public static void copyResources(AppComponent parent, AppComponent child) {
        String e = parent.iconSet().get();
        if (e != null) {
            Applications.visitComponentTree(child, new ComponentTreeVisitor() {
                @Override
                public boolean visit(AppComponent component) {
                    if (component.iconSet().get() == null) {
                        component.iconSet().set(e);
                        return true;
                    }
                    return false;
                }
            });
        }

        IconConfig ic = parent.iconConfig().get();
        if (ic != null) {
            Applications.visitComponentTree(child, new ComponentTreeVisitor() {
                @Override
                public boolean visit(AppComponent component) {
                    if (component.iconConfig().get() == null) {
                        component.iconConfig().set(ic);
                        return true;
                    }
                    return false;
                }
            });
        }
        Locale lc = parent.locale().get();
        if (lc != null) {
            Applications.visitComponentTree(child, new ComponentTreeVisitor() {
                @Override
                public boolean visit(AppComponent component) {
                    if (component.locale().get() == null) {
                        component.locale().set(lc);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public static void propagateResources(AppComponent parent, AppComponent child) {
        parent.iconSet().onChangeAndInit(() -> child.iconSet().set(parent.iconSet().get()));
        parent.iconConfig().onChangeAndInit(() -> child.iconConfig().set(parent.iconConfig().get()));
        parent.locale().onChangeAndInit(() -> child.locale().set(parent.locale().get()));
    }

    public static void bindContent(AppContentAdapter parent) {
        AppComponent child = parent.content();
        ((ComponentBase) child).setParent(parent);
        child.userObjects().put(AppContentAdapter.class.getName() + ":contentFor", parent);
        ((ComponentBase) parent).setPeer(new AppContentAdapterPeer(child));
        propagateResources(parent, child);
        parent.visible().bind(child.visible());
        parent.editable().bind(child.editable());
        parent.title().bind(child.title());
        parent.opaque().bind(child.opaque());
        parent.opaque().bind(child.opaque());
        parent.prefSize().bind(child.prefSize());
        parent.active().bind(child.active());
        parent.icon().bind(child.icon());
        parent.focused().bind(child.focused());
        parent.bounds().bind(child.bounds());
    }

    public static String getFileExtension(String name) {
        name = name.replace('\\', '/');
        int x = name.lastIndexOf('/');
        if (x >= 0) {
            name = name.substring(x + 1);
        }
        x = name.lastIndexOf('.');
        String suffix = "";
        if (x >= 0) {
            suffix = name.substring(x + 1);
        }
        return suffix;
    }

    public static byte[] toByteArray(InputStream in) {

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;

            // read bytes from the input stream and store them in buffer
            while ((len = in.read(buffer)) != -1) {
                // write bytes from the buffer into output stream
                os.write(buffer, 0, len);
            }

            return os.toByteArray();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static String toHex(int value, int pad) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(value));
        while (sb.length() < pad) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static Properties getExtensionToProperties() {
        if (EXT_TO_MIMETYPE != null) {
            return EXT_TO_MIMETYPE;
        }
        URL url = Applications.class.getResource("/net/thevpc/echo/extension-to-mimetype.properties");
        Properties p = new Properties();
        try (InputStream is = url.openStream()) {
            p.load(is);
        } catch (Exception ex) {
            //
        }
        Properties p2 = new Properties();
        for (Map.Entry<Object, Object> entry : p.entrySet()) {
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            String[] ks = k.trim().split(",");
            for (String k1 : ks) {
                p2.put(k1, v);
            }
        }
        return EXT_TO_MIMETYPE = p2;
    }

    public static String probeContentTypeByName(String path) {
        String fe = getFileExtension(path).toLowerCase();
        String a = getExtensionToProperties().getProperty(fe);
        if (a == null) {
            return "application/octet-stream";
        }
        return a;
    }

    public static String probeContentType(String path) {
        String probedContentType = null;
        File asFile = asFile(path);
        if (asFile != null) {
            try {
                probedContentType = Files.probeContentType(asFile.toPath());
            } catch (IOException ex) {
                //
            }
        }
        if (probedContentType == null) {
            URL asUrl = asURL(path);
            if (asUrl != null) {
                try {
                    URLConnection hc = asUrl.openConnection();
                    hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    probedContentType = hc.getContentType();
                } catch (IOException ex) {
                    //
                }
            }
        }
        if (probedContentType == null) {
            probedContentType = probeContentTypeByName(path);
        }
        if (probedContentType == null) {
            probedContentType = "application/octet-stream";
        }
        return probedContentType;
    }

    public static URL asURL(String url) {
        try {
            return new URL(url);
        } catch (Exception ex) {
        }
        //this is a file?
        File file1 = null;
        try {
            file1 = new File(url);
            return file1.toURI().toURL();
        } catch (Exception ex) {
        }
        return null;
    }

    public static File asFile(String url) {
        File file1 = null;
        try {
            if (url.startsWith("file:")) {
                if (url.contains(" ")) {//TODO: DIRTY fix me,
                    url = url.replaceAll(" ", "%20");
                }
                URL u = new URL(url);
                URI uri = u.toURI();

                if (uri.getAuthority() != null && uri.getAuthority().length() > 0) {
                    // Hack for UNC Path
                    uri = (new URL("file://" + url.substring("file:".length()))).toURI();
                }
                File file = new File(uri);
                if (file.exists()) {
                    return file;
                }
            }
            file1 = new File(url);
            if (file1.exists()) {
                return file1;
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public static Integer parseInt(String s) {
        if (s != null) {
            try {
                return Integer.parseInt(s);
            } catch (Exception ex) {
                //
            }
        }
        return null;
    }

    public static String getFileName(String s) {
        if (s == null) {
            return "";
        }
        int i = s.indexOf('?');
        if (i >= 0) {
            s = s.substring(0, i);
        }
        while (s.endsWith("/") || s.endsWith("\\")) {
            s = s.substring(0, s.length() - 1);
        }
        i = s.lastIndexOf('/');
        int i2 = s.lastIndexOf('\\');
        if (i2 > i) {
            i = i2;
        }
        if (i > 0) {
            return s.substring(i + 1);
        }
        return s;
    }

    public static AppImage loadIcon(String url, int width, int height, Application app) {
        URL u = asURL(url);
        if (u == null) {
            File f = asFile(url);
            if (f != null) {
                try {
                    u = f.toURI().toURL();
                } catch (MalformedURLException ex) {
                    throw new IllegalArgumentException("not an image : " + url);
                }
            }
        }
        if (u == null) {
            throw new IllegalArgumentException("not an image : " + url);
        }
        return new Image(u, app).scaleTo(width, height);
    }

    public static void setAllLocale(AppComponent component, Locale locale) {
        visitComponentTree(component, (c) -> {
            c.locale().set(locale);
            return true;
        });
    }

    public static void setAllIconSet(AppComponent component, String iconSet) {
        visitComponentTree(component, (c) -> {
            c.iconSet().set(iconSet);
            return true;
        });
    }

    public static void setAllIconConfig(AppComponent component, IconConfig iconConfig) {
        visitComponentTree(component, (c) -> {
            c.iconConfig().set(iconConfig);
            return true;
        });
    }

    public static void visitComponentTree(AppComponent component, ComponentTreeVisitor a) {
        Stack<AppComponent> stack = new Stack<>();
        stack.push(component);
        while (!stack.isEmpty()) {
            AppComponent curr = stack.pop();
            if (a.visit(curr)) {
                if (curr instanceof AppContainer) {
                    for (AppComponent child : ((AppContainer<?>) curr).children()) {
                        stack.push(child);
                    }
                }
                if (curr instanceof AppContentAdapter) {
                    stack.push(((AppContentAdapter) curr).content());
                }
                AppContextMenu c = curr.contextMenu().get();
                if (c != null) {
                    stack.push(c);
                }
            }
        }
    }

    public static String loadStreamAsString(URL url) {
        InputStream is = null;
        try {
            try {
                return new String(loadStreamAsByteArray(is = url.openStream()));
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static byte[] loadStreamAsByteArray(InputStream r) {
        ByteArrayOutputStream out = null;
        try {
            try {
                out = new ByteArrayOutputStream();
                copy(r, out, 8096);
                out.flush();
                return out.toByteArray();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static void copy(InputStream in, OutputStream out, int bufferSize) {
        try {
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static AppFrame frameOf(AppComponent child) {
        while (child != null) {
            if (child instanceof AppFrame) {
                return (AppFrame) child;
            }
            child = child.parent();
        }
        return null;
    }

    public static boolean isDeepChildOf(AppComponent child, AppComponent parent) {
        while (child != null) {
            if (child == parent) {
                return true;
            }
            child = child.parent();
        }
        return false;
    }

    public static AppComponent effectiveParent(AppComponent c) {
        if (c == null) {
            return c;
        }
        AppComponent e = (AppComponent) c.userObjects().get(AppContentAdapter.class.getName() + ":contentFor");
        if (e != null) {
            c = e;
        }
        return c.parent();
    }

    public interface ComponentTreeVisitor {

        boolean visit(AppComponent component);
    }

    public static class Helper {

        public static void runAfterStart(Application application, Runnable r) {
            if (application.state().get().ordinal() >= AppState.STARTED.ordinal()) {
                r.run();
            } else {
                application.state().onChange(event -> {
                    AppState e = event.newValue();
                    if (e == AppState.STARTED) {
                        r.run();
                    }
                });
            }
        }

    }

    public static class LastIndexTracker implements PropertyListener {

        private int lastIndex = -1;

        @Override
        public void propertyUpdated(PropertyEvent event) {
            Integer o = event.newValue();
            if (event.eventType() == PropertyUpdate.ADD) {
                lastIndex = o;
                WritableList<Integer> p = (WritableList<Integer>) event.property();
                if (p.size() == 1 && lastIndex != p.get(0)) {
                    // this happens because of the following scenario :
                    // add disabled selection index (example index 3)
                    // -> fire add next valid index (example index 4)
                    // -> fire selection changed (with previous index : here 3)
                    lastIndex = p.get(0);
                }
            }
        }

        public int getLastIndex() {
            return lastIndex;
        }
    }

    private static class AppContentAdapterPeer implements AppPanelPeer {

        private final AppComponent child;

        public AppContentAdapterPeer(AppComponent child) {
            this.child = child;
        }

        @Override
        public void install(AppComponent comp) {

        }

        @Override
        public Object toolkitComponent() {
            return child.peer().toolkitComponent();
        }

        @Override
        public void requestFocus() {
            child.peer().requestFocus();
        }
    }

}

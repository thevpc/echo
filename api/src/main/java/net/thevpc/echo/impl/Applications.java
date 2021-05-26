package net.thevpc.echo.impl;

import net.thevpc.common.i18n.I18n;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.echo.AppState;
import net.thevpc.echo.Application;
import net.thevpc.echo.Image;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.components.AppTextControl;
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
import java.util.function.Consumer;
import java.util.function.Predicate;

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
        return str.value(i18n,locale);
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

    public static <T> int bestSelectableIndex(ObservableList<T> list, Predicate<T> selectable, int currIndex, int lastIndex) {
        int bestIndex = -1;
        if (lastIndex <= currIndex) {
            bestIndex = nextSelectableIndex(list, selectable, currIndex);
            if (bestIndex < 0) {
                bestIndex = previousSelectableIndex(list, selectable, currIndex);
            }
        } else {
            bestIndex = previousSelectableIndex(list, selectable, currIndex);
            if (bestIndex < 0) {
                bestIndex = nextSelectableIndex(list, selectable, currIndex);
            }
        }
        return bestIndex;
    }

    //    public static AppChoiceItemRenderer<SimpleItem> prepareSimpleItemList(AppChoiceList<SimpleItem> c, Function<String, AppImage> functionResolver) {
//        LastIndexTracker lastIndexTracker = new LastIndexTracker();
//        c.selection().onChange(lastIndexTracker);
//        c.selection().adjusters().add(new PropertyAdjuster() {
//            @Override
//            public void adjust(PropertyAdjusterContext context) {
//                SimpleItem v = (SimpleItem) context.getNewValue();
//                if (v.isGroup()) {
//                    WritableList<SimpleItem> values = c.values();
//                    int newIndex = bestSelectableIndex(values, values.findFirstIndexOf(v), lastIndexTracker.getLastIndex());
//                    context.doInstead(() -> {
//                        c.selection().indices().add(newIndex);
//                    });
//                }
//            }
//        });
//        c.selection().onChange(new PropertyListener() {
//            private int lastIndex = -1;
//
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                SimpleItem v = c.selection().get();
//                if (v != null) {
//                    WritableList<SimpleItem> vals = c.values();
//                    int i = vals.findFirstIndex(a -> a.equals(v));
//                    if (v.isGroup()) {
//                        int bestIndex = -1;
//                        if (lastIndex <= i) {
//                            bestIndex = next(i);
//                            if (bestIndex < 0) {
//                                bestIndex = previous(i);
//                            }
//                        } else {
//                            bestIndex = previous(i);
//                            if (bestIndex < 0) {
//                                bestIndex = next(i);
//                            }
//                        }
//                        if (bestIndex < 0) {
//                            c.selection().clear();
//                        } else {
//                            c.selection().setAll(c.values().get(bestIndex));
//                        }
//                    }
//                    lastIndex = i;
//                }
//            }
//
//            private int next(int i) {
//                int bestIndex = -1;
//                for (int j = i + 1; j < c.values().size(); j++) {
//                    SimpleItem o2 = c.values().get(j);
//                    if (!o2.isGroup()) {
//                        bestIndex = j;
//                        break;
//                    }
//                }
//                return bestIndex;
//            }
//
//            private int previous(int i) {
//                int bestIndex = -1;
//                for (int j = i - 1; j >= 0; j--) {
//                    SimpleItem o2 = c.values().get(j);
//                    if (!o2.isGroup()) {
//                        bestIndex = j;
//                        break;
//                    }
//                }
//                return bestIndex;
//            }
//        });
//        c.itemRenderer().set(
//                context -> {
//                    SimpleItem value = context.getValue();
//                    if (value == null) {
//                        context.setIcon(null);
//                    } else {
//                        SimpleItem nv = value;
//                        if (nv.isGroup()) {
//                            AppFont f = context.getFont();
//                            AppColor fc = context.getColor();
//                            AppColor bc = context.getBackgroundColor();
//                            context.setOpaque(true);
//                            context.setBackgroundColor(fc);
//                            context.setTextColor(bc);
//                            context.setTextFont(f.derive(FontWeight.BOLD).derive(FontPosture.ITALIC));
//                        } else {
//                            String icon = (value).getIcon();
//                            AppImage iconObj = null;
//                            if (functionResolver != null) {
//                                iconObj = functionResolver.apply(icon);
//                            }
//                            context.setIcon(iconObj);
//                        }
//                    }
//                }
//        );
//    }
//
    private static <T> int nextSelectableIndex(ObservableList<T> list, Predicate<T> selectable, int i) {
        int bestIndex = -1;
        for (int j = i + 1; j < list.size(); j++) {
            T o2 = list.get(j);
            if (!selectable.test(o2)) {
                bestIndex = j;
                break;
            }
        }
        return bestIndex;
    }

    private static <T> int previousSelectableIndex(ObservableList<T> list, Predicate<T> selectable, int i) {
        int bestIndex = -1;
        for (int j = i - 1; j >= 0; j--) {
            T o2 = list.get(j);
            if (!selectable.test(o2)) {
                bestIndex = j;
                break;
            }
        }
        return bestIndex;
    }

    public static void bindContent(AppComponent parent, AppComponent child) {
        ((ComponentBase) child).setParent(parent);
        ((ComponentBase) parent).setPeer(new AppPanelPeer() {
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
        });
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

    public static String trim(String in) {
        return in == null ? "" : in.trim();
    }

    public static boolean isBlank(String in) {
        return in == null || in.trim().isEmpty();
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
        });
    }

    public static void setAllIconSet(AppComponent component, String iconSet) {
        visitComponentTree(component, (c) -> {
            c.iconSet().set(iconSet);
        });
    }

    public static void visitComponentTree(AppComponent component, Consumer<AppComponent> a) {
        Stack<AppComponent> stack = new Stack<>();
        stack.push(component);
        while (!stack.isEmpty()) {
            AppComponent curr = stack.pop();
            a.accept(curr);
            if (curr instanceof AppContainer) {
                for (AppComponent child : ((AppContainer<?>) curr).children()) {
                    stack.push(child);
                }
            }
        }

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
}

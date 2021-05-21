package net.thevpc.echo.impl;

import net.thevpc.common.i18n.I18n;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.echo.*;
import net.thevpc.echo.api.AppContainerChildren;
import net.thevpc.echo.api.AppUIPlaf;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.iconset.IconSet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Applications {

    private Applications() {
    }

    public static String rawString(Str str, Application app) {
        return rawString(str, app.i18n());
    }

    public static String rawString(Str str, I18n i18n) {
        if (str == null) {
            return "";
        }
        if (!str.is18n()) {
            return str.value();
        }
        return i18n.getString(str.value());
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
            if(event.eventType()==PropertyUpdate.ADD) {
                lastIndex = o;
                WritableList<Integer> p = (WritableList<Integer>) event.property();
                if(p.size()==1 && lastIndex!= p.get(0)){
                    // this happens because of the following scenario :
                    // add disabled selection index (example index 3)
                    // -> fire add next valid index (example index 4)
                    // -> fire selection changed (with previous index : here 3)
                    lastIndex = p.get(0);
                }
//                System.out.println(" !! lastIndex="+lastIndex+"  "+event.property());
            }else{
//                System.out.println(" !! ignore "+ o +"  "+event.property());
            }
        }

        public int getLastIndex() {
            return lastIndex;
        }
    }
}

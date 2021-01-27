package net.thevpc.echo;

import java.util.EnumSet;
import java.util.Set;

public class AppWindowStateSet {
    private EnumSet<AppWindowState> values = EnumSet.of(AppWindowState.NORMAL);

    public AppWindowStateSet() {
    }

    private AppWindowStateSet(Set<AppWindowState> values0) {
        values.addAll(values0);
    }

    private static void add(EnumSet<AppWindowState> values, AppWindowState a) {
        switch (a) {
            case ACTIVATED: {
                values.add(AppWindowState.ACTIVATED);
                values.remove(AppWindowState.DEACTIVATED);
                break;
            }
            case DEACTIVATED: {
                values.add(AppWindowState.DEACTIVATED);
                values.remove(AppWindowState.ACTIVATED);
                break;
            }
            case NORMAL: {
                values.add(AppWindowState.NORMAL);
                values.remove(AppWindowState.ICONIFIED);
                values.remove(AppWindowState.DEACTIVATED);
                values.remove(AppWindowState.MAXIMIZED_BOTH);
                values.remove(AppWindowState.MAXIMIZED_HORIZ);
                values.remove(AppWindowState.MAXIMIZED_VERT);
                break;
            }
            case MAXIMIZED_VERT: {
                values.add(AppWindowState.MAXIMIZED_VERT);
                values.remove(AppWindowState.NORMAL);
                values.remove(AppWindowState.ICONIFIED);
                if (values.contains(AppWindowState.MAXIMIZED_HORIZ)) {
                    values.add(AppWindowState.MAXIMIZED_BOTH);
                }
                break;
            }
            case MAXIMIZED_HORIZ: {
                values.add(AppWindowState.MAXIMIZED_HORIZ);
                values.remove(AppWindowState.NORMAL);
                values.remove(AppWindowState.ICONIFIED);
                if (values.contains(AppWindowState.MAXIMIZED_VERT)) {
                    values.add(AppWindowState.MAXIMIZED_BOTH);
                }
                break;
            }
            case MAXIMIZED_BOTH: {
                values.add(AppWindowState.MAXIMIZED_BOTH);
                values.add(AppWindowState.MAXIMIZED_VERT);
                values.add(AppWindowState.MAXIMIZED_HORIZ);
                values.remove(AppWindowState.NORMAL);
                values.remove(AppWindowState.ICONIFIED);
                break;
            }
            case ICONIFIED: {
                values.add(AppWindowState.ICONIFIED);
                values.remove(AppWindowState.DEICONIFIED);
                break;
            }
            case DEICONIFIED: {
                values.add(AppWindowState.DEICONIFIED);
                values.remove(AppWindowState.ICONIFIED);
                break;
            }
            case CLOSED: {
                values.add(AppWindowState.CLOSED);
                values.remove(AppWindowState.CLOSING);
                values.remove(AppWindowState.OPENED);
                values.remove(AppWindowState.OPENING);
                break;
            }
            case CLOSING: {
                values.add(AppWindowState.CLOSING);
                values.remove(AppWindowState.CLOSED);
                values.remove(AppWindowState.OPENED);
                values.remove(AppWindowState.OPENING);
                break;
            }
            case OPENING: {
                values.add(AppWindowState.OPENING);
                values.remove(AppWindowState.CLOSED);
                values.remove(AppWindowState.OPENED);
                values.remove(AppWindowState.CLOSING);
                break;
            }
            case OPENED: {
                values.add(AppWindowState.OPENED);
                values.remove(AppWindowState.CLOSED);
                values.remove(AppWindowState.OPENING);
                values.remove(AppWindowState.CLOSING);
                break;
            }
        }
    }

    public boolean is(AppWindowState a) {
        return values.contains(a);
    }

    public Set<AppWindowState> values() {
        return values;
    }

    public AppWindowStateSet add(AppWindowState a) {
        EnumSet<AppWindowState> values0 = EnumSet.noneOf(AppWindowState.class);
        values0.addAll(values);
        add(values0, a);
        return new AppWindowStateSet(values0);
    }

    @Override
    public String toString() {
        return String.valueOf(values);
    }
    
}

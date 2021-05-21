package net.thevpc.echo;

import java.util.EnumSet;
import java.util.Set;

public class WindowStateSet {
    private EnumSet<WindowState> values = EnumSet.of(WindowState.NORMAL);

    public WindowStateSet() {
    }

    private WindowStateSet(Set<WindowState> values0) {
        values.addAll(values0);
    }

    private static void add(EnumSet<WindowState> values, WindowState a) {
        switch (a) {
            case ACTIVATED: {
                values.add(WindowState.ACTIVATED);
                values.remove(WindowState.DEACTIVATED);
                break;
            }
            case DEACTIVATED: {
                values.add(WindowState.DEACTIVATED);
                values.remove(WindowState.ACTIVATED);
                break;
            }
            case NORMAL: {
                values.add(WindowState.NORMAL);
                values.remove(WindowState.ICONIFIED);
                values.remove(WindowState.DEACTIVATED);
                values.remove(WindowState.MAXIMIZED_BOTH);
                values.remove(WindowState.MAXIMIZED_HORIZ);
                values.remove(WindowState.MAXIMIZED_VERT);
                break;
            }
            case MAXIMIZED_VERT: {
                values.add(WindowState.MAXIMIZED_VERT);
                values.remove(WindowState.NORMAL);
                values.remove(WindowState.ICONIFIED);
                if (values.contains(WindowState.MAXIMIZED_HORIZ)) {
                    values.add(WindowState.MAXIMIZED_BOTH);
                }
                break;
            }
            case MAXIMIZED_HORIZ: {
                values.add(WindowState.MAXIMIZED_HORIZ);
                values.remove(WindowState.NORMAL);
                values.remove(WindowState.ICONIFIED);
                if (values.contains(WindowState.MAXIMIZED_VERT)) {
                    values.add(WindowState.MAXIMIZED_BOTH);
                }
                break;
            }
            case MAXIMIZED_BOTH: {
                values.add(WindowState.MAXIMIZED_BOTH);
                values.add(WindowState.MAXIMIZED_VERT);
                values.add(WindowState.MAXIMIZED_HORIZ);
                values.remove(WindowState.NORMAL);
                values.remove(WindowState.ICONIFIED);
                break;
            }
            case ICONIFIED: {
                values.add(WindowState.ICONIFIED);
                values.remove(WindowState.DEICONIFIED);
                break;
            }
            case DEICONIFIED: {
                values.add(WindowState.DEICONIFIED);
                values.remove(WindowState.ICONIFIED);
                break;
            }
            case CLOSED: {
                values.add(WindowState.CLOSED);
                values.remove(WindowState.CLOSING);
                values.remove(WindowState.OPENED);
                values.remove(WindowState.OPENING);
                break;
            }
            case CLOSING: {
                values.add(WindowState.CLOSING);
                values.remove(WindowState.CLOSED);
                values.remove(WindowState.OPENED);
                values.remove(WindowState.OPENING);
                break;
            }
            case OPENING: {
                values.add(WindowState.OPENING);
                values.remove(WindowState.CLOSED);
                values.remove(WindowState.OPENED);
                values.remove(WindowState.CLOSING);
                break;
            }
            case OPENED: {
                values.add(WindowState.OPENED);
                values.remove(WindowState.CLOSED);
                values.remove(WindowState.OPENING);
                values.remove(WindowState.CLOSING);
                break;
            }
        }
    }
    private static void remove(EnumSet<WindowState> values, WindowState a) {
        switch (a) {
            case ACTIVATED: {
                values.add(WindowState.DEACTIVATED);
                values.remove(WindowState.ACTIVATED);
                break;
            }
            case DEACTIVATED: {
                values.add(WindowState.ACTIVATED);
                values.remove(WindowState.DEACTIVATED);
                break;
            }
            case NORMAL: {
                values.add(WindowState.NORMAL);
                values.remove(WindowState.ICONIFIED);
                values.remove(WindowState.DEACTIVATED);
                values.remove(WindowState.MAXIMIZED_BOTH);
                values.remove(WindowState.MAXIMIZED_HORIZ);
                values.remove(WindowState.MAXIMIZED_VERT);
                break;
            }
            case MAXIMIZED_VERT: {
                values.remove(WindowState.MAXIMIZED_VERT);
                values.add(WindowState.NORMAL);
                if (values.contains(WindowState.MAXIMIZED_BOTH)) {
                    values.remove(WindowState.MAXIMIZED_HORIZ);
                }
                break;
            }
            case MAXIMIZED_HORIZ: {
                values.remove(WindowState.MAXIMIZED_HORIZ);
                values.add(WindowState.NORMAL);
                if (values.contains(WindowState.MAXIMIZED_BOTH)) {
                    values.remove(WindowState.MAXIMIZED_VERT);
                }
                break;
            }
            case MAXIMIZED_BOTH: {
                values.remove(WindowState.MAXIMIZED_BOTH);
                values.remove(WindowState.MAXIMIZED_VERT);
                values.remove(WindowState.MAXIMIZED_HORIZ);
                values.add(WindowState.NORMAL);
                break;
            }
            case ICONIFIED: {
                values.remove(WindowState.ICONIFIED);
                values.add(WindowState.DEICONIFIED);
                break;
            }
            case DEICONIFIED: {
                values.remove(WindowState.DEICONIFIED);
                values.add(WindowState.ICONIFIED);
                break;
            }
            case CLOSED: {
                values.remove(WindowState.CLOSED);
                break;
            }
            case CLOSING: {
                values.remove(WindowState.CLOSING);
                break;
            }
            case OPENING: {
                values.remove(WindowState.OPENING);
                break;
            }
            case OPENED: {
                values.remove(WindowState.OPENED);
                break;
            }
        }
    }

    public boolean is(WindowState a) {
        return values.contains(a);
    }

    public Set<WindowState> values() {
        return values;
    }

    public WindowStateSet add(WindowState a) {
        EnumSet<WindowState> values0 = EnumSet.noneOf(WindowState.class);
        values0.addAll(values);
        add(values0, a);
        return new WindowStateSet(values0);
    }
    public WindowStateSet remove(WindowState a) {
        EnumSet<WindowState> values0 = EnumSet.noneOf(WindowState.class);
        values0.addAll(values);
        add(values0, a);
        return new WindowStateSet(values0);
    }

    @Override
    public String toString() {
        return String.valueOf(values);
    }
    
}

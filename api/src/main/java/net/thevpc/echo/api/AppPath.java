package net.thevpc.echo.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AppPath {

    private String[] items;

    private AppPath(String... items) {
        List<String> a = new ArrayList<>();
        if (items != null) {
            for (String item : items) {
                if (item != null) {
                    for (String s : item.split("[/\\\\]+")) {
                        if (!s.isEmpty()) {
                            a.add(s);
                        }
                    }
                }
            }
        }
        this.items = a.toArray(new String[0]);
    }

    private AppPath(List<String> items) {
        List<String> a = new ArrayList<>();
        if (items != null) {
            for (String item : items) {
                if (item != null) {
                    for (String s : item.split("[/\\\\]+")) {
                        if (!s.isEmpty()) {
                            a.add(s);
                        }
                    }
                }
            }
        }
        this.items = a.toArray(new String[0]);
    }

    public static AppPath of(String... items) {
        return new AppPath(items);
    }

    public String get(int index) {
        return items[index];
    }

    public AppPath child(AppPath path) {
        return path == null ? this : append(path.items);
    }

    public boolean startsWith(AppPath child) {
        if (size() >= child.size()) {
            for (int i = 0; i < child.size(); i++) {
                String s = this.get(i);
                String o = child.get(i);
                if (!Objects.equals(s, o)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public AppPath append(String... items) {
        if (items == null || items.length == 0) {
            return this;
        }
        List<String> a = new ArrayList<>(Arrays.asList(this.items));
        a.addAll(Arrays.asList(items));
        return new AppPath(a);
    }
    public AppPath append(AppPath items) {
        if (items == null || items.size() == 0) {
            return this;
        }
        List<String> a = new ArrayList<>(Arrays.asList(this.items));
        a.addAll(Arrays.asList(items.items));
        return new AppPath(a);
    }

    public AppPath parent() {
        if (items.length == 0) {
            return this;
        }
        return new AppPath(Arrays.copyOf(items, items.length - 1));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppPath appPath = (AppPath) o;
        return Arrays.equals(items, appPath.items);
    }

    public String toString() {
        if (items.length == 0) {
            return "/";
        }
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append("/").append(item);
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return items.length == 0;
    }

    public int size() {
        return items.length;
    }

    public String name() {
        return items.length == 0 ? null : items[items.length - 1];
    }

    public String last() {
        return name();
    }

    public String first() {
        return items.length == 0 ? null : items[0];
    }

    public AppPath skipLast() {
        return items.length == 0 ? this : of(Arrays.copyOfRange(items, 0, items.length - 1));
    }

    public AppPath skipFirst() {
        return items.length == 0 ? this : of(Arrays.copyOfRange(items, 1, items.length));
    }

    public AppPath subPath(int from) {
        return AppPath.of(
                Arrays.copyOfRange(items, from, items.length)
        );
    }

    public AppPath subPath(int from, int to) {
        return AppPath.of(
                Arrays.copyOfRange(items, from, to)
        );
    }

    public AppPath tail(int size) {
        return AppPath.of(
                Arrays.copyOfRange(items, items.length - size, items.length)
        );
    }

    public AppPath head(int size) {
        return AppPath.of(
                Arrays.copyOfRange(items, 0, size)
        );
    }
}

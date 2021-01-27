package net.thevpc.echo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemPath {
    private String[] items;

    private ItemPath(String... items) {
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

    private ItemPath(List<String> items) {
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

    public static ItemPath of(String... items) {
        return new ItemPath(items);
    }

    public String get(int index) {
        return items[index];
    }

    public ItemPath child(ItemPath path) {
        return path == null ? this : child(path.items);
    }

    public ItemPath child(String... items) {
        if (items == null || items.length == 0) {
            return this;
        }
        List<String> a = new ArrayList<>(Arrays.asList(this.items));
        a.addAll(Arrays.asList(items));
        return new ItemPath(a);
    }

    public ItemPath parent() {
        if (items.length == 0) {
            return this;
        }
        return new ItemPath(Arrays.copyOf(items, items.length - 1));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPath itemPath = (ItemPath) o;
        return Arrays.equals(items, itemPath.items);
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

    public String first() {
        return items.length == 0 ? null : items[0];
    }

    public ItemPath skipLast() {
        return items.length == 0 ? this : of(Arrays.copyOfRange(items, 0, items.length - 1));
    }

    public ItemPath skipFirst() {
        return items.length == 0 ? this : of(Arrays.copyOfRange(items, 1, items.length));
    }
}

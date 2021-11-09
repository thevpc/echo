/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thevpc
 */
public class IndexedMap<K, V> {

    private Map<K, V> values = new HashMap<>();
    private List<K> keys = new ArrayList<>();

    public boolean containsKey(K k) {
        return values.containsKey(k);
    }

    public K getKeyAt(int index) {
        return keys.get(index);
    }

    public V getValueAt(int index) {
        return values.get(keys.get(index));
    }

    public V get(K k) {
        return values.remove(k);
    }

    public V remove(K k) {
        if (values.containsKey(k)) {
            V old = values.remove(k);
            keys.remove(k);
            return old;
        }
        return null;
    }

    public V add(K k, V v) {
        if (values.containsKey(k)) {
            V old = values.put(k, v);
            keys.add(k);
            return old;
        } else {
            values.put(k, v);
            keys.add(k);
            return null;
        }
    }

}

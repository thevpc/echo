/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 *
 * Copyright [2021] [thevpc] Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * <br> ====================================================================
 */



package net.thevpc.echo.impl.util;

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

    public int indexOfKey(K id) {
        return keys.indexOf(id);
    }

    public K getKeyAt(int index) {
        return keys.get(index);
    }

    public V getValueAt(int index) {
        return values.get(keys.get(index));
    }

    public V get(K k) {
        return values.get(k);
    }

    public V remove(K k) {
        if (values.containsKey(k)) {
            V old = values.remove(k);
            keys.remove(k);
            return old;
        }
        return null;
    }

    public boolean isEmpty() {
        return keys.isEmpty();
    }

    public int size() {
        return keys.size();
    }

    public V put(K k, V v) {
        if (values.containsKey(k)) {
            V o = values.put(k, v);
            return o;
        } else {
            values.put(k, v);
            keys.add(k);
            return null;
        }
    }

    public void clear() {
        values.clear();
        keys.clear();
    }

}

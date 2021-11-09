/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.raw;

import java.util.NoSuchElementException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import net.thevpc.echo.impl.util.IndexedMap;

/**
 *
 * @author thevpc
 */
public class FxTabbedContainer extends BorderPane {

    private class TabInfo {

        String id;
        String title;
        Node node;
        Image icon;
        boolean closable;
    }

    private IndexedMap<String, TabInfo> components = new IndexedMap<>();
    private boolean _closing;
    private TabRemoveHandler tabRemoveHandler = new TabRemoveHandler();

    public FxTabbedContainer() {
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    public boolean containsId(String id) {
        return components.containsKey(id);
    }
    
    public void remove(String id) {
        if (!_closing) {
            _closing = true;
            try {
                remove0(id, true);
            } finally {
                _closing = false;
            }
        } else {
            remove0(id, true);
        }
    }

    public void remove0(String id, boolean removeTab) {
        if (components.containsKey(id)) {
            if (components.size() == 1) {
                components.clear();
                setCenter(null);
            } else {
                TabPane tabs = getCenterTab();
                int index = components.indexOfKey(id);
                if (removeTab) {
                    tabs.getTabs().remove(index);
                }
                components.remove(id);
                if (components.size() == 1) {
                    setCenter(components.getValueAt(0).node);
                } else {
                    for (int i = 0; i < components.size(); i++) {
                        Tab tab = tabs.getTabs().get(index);
                        prepareTab(tab, id, index);
                    }
                }
            }
        }
    }

    protected TabPane getCenterTab() {
        return (TabPane) getCenter();
    }

    public void setTabTitle(String id, String title) {
        TabInfo ti = components.get(id);
        if (ti == null) {
            throw new NoSuchElementException();
        }
        ti.title = title;
        rebuildTab(id);
    }

    public void setTabIcon(String id, Image icon) {
        TabInfo ti = components.get(id);
        if (ti == null) {
            throw new NoSuchElementException();
        }
        ti.icon = icon;
        rebuildTab(id);
    }

    public void setTabClosable(String id, boolean closable) {
        TabInfo ti = components.get(id);
        if (ti == null) {
            throw new NoSuchElementException();
        }
        ti.closable = closable;
        rebuildTab(id);
    }

    protected void rebuildTab(String id) {
        if (components.size() > 1) {
            int index = components.indexOfKey(id);
            if (index > 0) {
                Tab tab = getCenterTab().getTabs().get(index);
                prepareTab(tab, id, index);
            }
        }
    }

    public void prepareTab(Tab tab, String id, int index) {
        TabInfo ti = components.getValueAt(index);
        tab.setText(ti.title);
        tab.getProperties().put("tabIndex", index);
        tab.closableProperty().set(ti.closable);
    }

    public void add(String id, Node n, String title, Image icon, boolean closable) {
        if (components.containsKey(id)) {
            TabInfo ti = components.get(id);
            ti.id = id;
            ti.title = title;
            ti.icon = icon;
            ti.closable = closable;
            ti.node = n;
            if (components.size() == 1) {
                components.put(id, ti);
                setCenter(n);
            } else {
                TabPane p = getCenterTab();
                int index = components.indexOfKey(id);
                Tab tab = p.getTabs().get(index);
                tab.setContent(n);
                prepareTab(tab, id, index);
            }
        } else {
            TabInfo ti = new TabInfo();
            ti.id = id;
            ti.title = title;
            ti.icon = icon;
            ti.closable = closable;
            ti.node = n;
            if (components.isEmpty()) {
                components.put(id, ti);
                setCenter(n);
            } else {
                TabPane tabs = null;
                if (components.size() == 1) {
                    tabs = new TabPane();
                    Tab tab = new Tab();
                    String k0 = components.getKeyAt(0);
                    TabInfo ti0 = components.getValueAt(0);
                    tab.setContent(ti0.node);
                    tab.setOnClosed(tabRemoveHandler);
                    tabs.getTabs().add(tab);
                    prepareTab(tab, k0, 0);
                    setCenter(tabs);
                } else {
                    tabs = getCenterTab();
                }
                Tab tab = new Tab();
                tab.setOnClosed(tabRemoveHandler);
                tabs.getTabs().add(tab);
                components.put(id, ti);
                tab.setContent(n);
                prepareTab(tab, id, components.size() - 1);
            }
        }
    }

    private class TabRemoveHandler implements EventHandler<Event> {

        public TabRemoveHandler() {
        }

        @Override
        public void handle(Event t) {
            Tab tab = (Tab) t.getSource();
            if (!_closing) {
                int i = (int) tab.getProperties().get("tabIndex");
                String theId = components.getKeyAt(i);
                remove0(theId, false);
            }
        }
    }


}

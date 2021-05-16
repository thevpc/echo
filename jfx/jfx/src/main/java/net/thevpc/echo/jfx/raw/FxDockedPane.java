/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.raw;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author vpc
 */
public class FxDockedPane extends BorderPane {

    private FxTabbedContainer[] all = new FxTabbedContainer[Anchor.values().length];

    public FxDockedPane() {
    }

    public void add(String id, Anchor anchor, String title, Image icon, Node n, boolean closable) {
        Anchor old = lookupAnchor(id);
        if (old != null) {
            throw new IllegalArgumentException("already found");
        }
        if (all[anchor.ordinal()] == null) {
            all[anchor.ordinal()] = new FxTabbedContainer();
        }
        all[anchor.ordinal()].add(id, n, title, icon, closable);
        rebuild();
    }

    public void setTabTitle(String id, String title) {
        FxTabbedContainer a = all[lookupAnchorErr(id).ordinal()];
        if (a != null) {
            a.setTabTitle(id, title);
        }
    }

    public void setTabIcon(String id, Image icon) {
        FxTabbedContainer a = all[lookupAnchorErr(id).ordinal()];
        if (a != null) {
            a.setTabIcon(id, icon);
        }
    }

    public void setTabClosable(String id, boolean closable) {
        FxTabbedContainer a = all[lookupAnchorErr(id).ordinal()];
        if (a != null) {
            a.setTabClosable(id, closable);
        }
    }

    public Anchor lookupAnchorErr(String id) {
        Anchor a = lookupAnchor(id);
        if (a == null) {
            throw new IllegalArgumentException("no such element " + id);
        }
        return a;
    }

    public Anchor lookupAnchor(String id) {
        for (int i = 0; i < all.length; i++) {
            FxTabbedContainer a = all[i];
            if (a != null) {
                if (a.containsId(id)) {
                    return Anchor.values()[i];
                }
            }
        }
        return null;
    }

    public void remove(String id) {
        Anchor anchor = lookupAnchor(id);
        if (anchor == null) {
            return;
        }
        if (all[anchor.ordinal()] != null) {
            all[anchor.ordinal()].remove(id);
            if (all[anchor.ordinal()].isEmpty()) {
                all[anchor.ordinal()] = null;
            }
        }
        rebuild();
    }

    private void rebuild() {
        Node n = rebuildV();
        super.setCenter(n);
    }

    private Node rebuildV() {
        List<Node> all2 = new ArrayList<>();
        if (all[Anchor.TOP.ordinal()] != null) {
            all2.add(all[Anchor.TOP.ordinal()]);
        }
        all2.add(rebuildH());
        if (all[Anchor.BOTTOM.ordinal()] != null) {
            all2.add(all[Anchor.BOTTOM.ordinal()]);
        }
        if (all2.size() == 0) {
            return new Label();
        }
        if (all2.size() == 1) {
            return all2.get(0);
        }
        SplitPane s = new SplitPane();
        s.setOrientation(Orientation.VERTICAL);
        s.getItems().setAll(all2);
        return s;
    }

    private Node rebuildH() {
        List<FxTabbedContainer> all2 = new ArrayList<>();
        if (all[Anchor.LEFT.ordinal()] != null) {
            all2.add(all[Anchor.LEFT.ordinal()]);
        }
        if (all[Anchor.CENTER.ordinal()] != null) {
            all2.add(all[Anchor.CENTER.ordinal()]);
        }
        if (all[Anchor.RIGHT.ordinal()] != null) {
            all2.add(all[Anchor.RIGHT.ordinal()]);
        }
        if (all2.size() == 0) {
            return new Label();
        }
        if (all2.size() == 1) {
            return all2.get(0);
        }
        SplitPane s = new SplitPane();
        s.setOrientation(Orientation.HORIZONTAL);
        s.getItems().setAll(all2);
        return s;
    }

    public static enum Anchor {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        CENTER,
    }

}

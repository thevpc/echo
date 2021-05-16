package net.thevpc.echo.swing.raw;


import net.thevpc.swing.plaf.UIPlafManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JDockPane extends JPanel{
    public static void main(String[] args) {
        UIPlafManager.getCurrentManager().apply(UIPlafManager.getCurrentManager().items().get(15));
        JDockPane dp=new JDockPane();
        for (DockAnchor value : DockAnchor.values()) {
            for (int i = 0; i < 3; i++) {
                String s=" "+(i+1);
                dp.add(value.toString()+s,
                        new JLabel(value.toString()+s),
                        value.toString()+s,null,false,value);
            }
        }
        dp.setPreferredSize(new Dimension(600,400));
        JOptionPane.showMessageDialog(null,dp);
    }

    private IDockTools[] all = new IDockTools[DockAnchor.values().length];

    public JDockPane() {
        super(new BorderLayout());
    }

    public void add(String id, JComponent n, String title, Icon icon, boolean closable, DockAnchor anchor) {
        DockAnchor old = lookupAnchor(id);
        if (old != null) {
            throw new IllegalArgumentException("already found");
        }
        if (all[anchor.ordinal()] == null) {
            if(anchor==DockAnchor.CENTER){
                all[anchor.ordinal()] = new TabsTools();
            }else {
                all[anchor.ordinal()] = new JDockTools(anchor);
            }
        }
        all[anchor.ordinal()].add(id, n, title, icon, closable);
        rebuild();
    }


    public DockAnchor lookupAnchor(String id) {
        for (int i = 0; i < all.length; i++) {
            IDockTools a = all[i];
            if (a != null) {
                if (a.containsId(id)) {
                    return DockAnchor.values()[i];
                }
            }
        }
        return null;
    }

    public void remove(String id) {
        DockAnchor anchor = lookupAnchor(id);
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
        JComponent n = rebuildV();
        add(n, BorderLayout.CENTER);
    }

    private JComponent rebuildV() {
        List<JComponent > all2 = new ArrayList<>();
        if (all[DockAnchor.TOP.ordinal()] != null) {
            all2.add((JComponent) all[DockAnchor.TOP.ordinal()]);
        }
        all2.add(rebuildH());
        if (all[DockAnchor.BOTTOM.ordinal()] != null) {
            all2.add((JComponent) all[DockAnchor.BOTTOM.ordinal()]);
        }
        if (all2.size() == 0) {
            return new JLabel();
        }
        if (all2.size() == 1) {
            return all2.get(0);
        }
        JSplitPane s = new JSplitPane();
        s.setOrientation(JSplitPane.VERTICAL_SPLIT);
        s.add(all2.get(0),JSplitPane.LEFT);
        s.add(all2.get(1),JSplitPane.RIGHT);
        if(all2.size()==3){
            JSplitPane s2 = new JSplitPane();
            s2.setOrientation(JSplitPane.VERTICAL_SPLIT);
            s2.add(s,JSplitPane.LEFT);
            s2.add(all2.get(2),JSplitPane.RIGHT);
            return s2;
        }
        return s;
    }

    private JComponent rebuildH() {
        List<JComponent> all2 = new ArrayList<>();
        if (all[DockAnchor.LEFT.ordinal()] != null) {
            all2.add((JComponent)all[DockAnchor.LEFT.ordinal()]);
        }
        if (all[DockAnchor.CENTER.ordinal()] != null) {
            all2.add((JComponent) all[DockAnchor.CENTER.ordinal()]);
        }
        if (all[DockAnchor.RIGHT.ordinal()] != null) {
            all2.add((JComponent) all[DockAnchor.RIGHT.ordinal()]);
        }
        if (all2.size() == 0) {
            return new JLabel();
        }
        if (all2.size() == 1) {
            return all2.get(0);
        }
        JSplitPane s = new JSplitPane();
        s.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        s.add(all2.get(0),JSplitPane.LEFT);
        s.add(all2.get(1),JSplitPane.RIGHT);
        if(all2.size()==3){
            JSplitPane s2 = new JSplitPane();
            s2.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            s2.add(s,JSplitPane.LEFT);
            s2.add(all2.get(2),JSplitPane.RIGHT);
            return s2;
        }
        return s;
    }

    public interface IDockTools {
        void add(String id, JComponent component, String title, Icon icon, boolean closable);

        boolean containsId(String id);

        void remove(String id);

        boolean isEmpty();
    }

    public static class TabsTools extends JTabbedContainer implements IDockTools {


    }

    public enum DockAnchor{
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        CENTER,
    }
}

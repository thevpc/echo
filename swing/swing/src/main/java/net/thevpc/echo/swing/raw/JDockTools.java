package net.thevpc.echo.swing.raw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class JDockTools extends JPanel implements JDockPane.IDockTools {
    List<JDockToolAndButton> windows = new ArrayList<>();
    JDockToolAndButton selected;
    JToolBar bar;
    JPanel pane;
    JDockPane.DockAnchor anchor;
    ButtonGroup bg = new ButtonGroup(){
        @Override
        public void setSelected(ButtonModel model, boolean selected) {
            if (selected) {
                super.setSelected(model, selected);
            } else {
                //super.setSelected(model, selected);
                clearSelection();
            }
        }
    };

    public JDockTools(JDockPane.DockAnchor anchor) {
        super(new BorderLayout());
        pane = new JPanel(new BorderLayout());
        this.add(pane,BorderLayout.CENTER);
        bar = new JToolBar();
        bar.setFloatable(false);
        this.anchor = anchor;
        switch (this.anchor) {
            case TOP: {
                bar.setOrientation(JToolBar.HORIZONTAL);
                pane.add(bar, BorderLayout.NORTH);
                break;
            }
            case BOTTOM: {
                bar = new JToolBar(JToolBar.HORIZONTAL);
                pane.add(bar, BorderLayout.SOUTH);
                break;
            }
            case LEFT: {
                bar.setOrientation(JToolBar.VERTICAL);
                pane.add(bar, BorderLayout.WEST);
                break;
            }
            case RIGHT: {
                bar = new JToolBar(JToolBar.VERTICAL);
                pane.add(bar, BorderLayout.EAST);
                break;
            }
            case CENTER: {

            }
        }
    }

    @Override
    public boolean containsId(String id) {
        return windows.stream().anyMatch(x->x.win.id.equals(id));
    }

    @Override
    public void remove(String id) {
        JDockToolAndButton a = windows.stream().filter(x -> x.win.id.equals(id)).findFirst().orElse(null);
        if(a!=null){
            if(selected==a){
                add(null, BorderLayout.CENTER);
            }
            windows.remove(a);
            bar.remove(a.button);
            bg.remove(a.button);
        }
    }

    @Override
    public boolean isEmpty() {
        return windows.isEmpty();
    }

    public void add(String id, JComponent component, String title, Icon icon, boolean closable) {
        JToggleButton t = new JToggleButton(title);
        t.setMargin(new Insets(2,2,2,2));
        t.setFont(t.getFont().deriveFont(t.getFont().getSize()*0.8f));
        if(anchor== JDockPane.DockAnchor.LEFT) {
            t.setUI(new VerticalButtonUI(270));
        }else if(anchor== JDockPane.DockAnchor.RIGHT) {
            t.setUI(new VerticalButtonUI(90));
        }
        JDockToolAndButton tb = new JDockToolAndButton(
                new JDockWindow(id, title, icon, component, closable)
                , t
        );
        t.putClientProperty("JDockToolAndButton", tb);
        bg.add(t);
        t.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    boolean oldNoSelection=selected==null;
                    selected = (JDockToolAndButton) t.getClientProperty("JDockToolAndButton");
                    pane.add(selected.win, BorderLayout.CENTER);
                    if(oldNoSelection){
                        if(getParent() instanceof JSplitPane){
                            JSplitPane jsp=(JSplitPane) getParent();
                            jsp.setDividerLocation(jsp.getLastDividerLocation());
                        }
                    }
                }
                boolean someSelection=false;
                for (Component c : bar.getComponents()) {
                    if(c instanceof JToggleButton){
                        if(bg.isSelected(((JToggleButton) c).getModel())){
                            someSelection=true;
                            break;
                        }
                    }
                }
                if(!someSelection){
                    if(selected!=null) {
                        pane.remove(selected.win);
                        selected = null;
                        if(getParent() instanceof JSplitPane){
                            JSplitPane jsp=(JSplitPane) getParent();
                            if(anchor== JDockPane.DockAnchor.LEFT ||anchor== JDockPane.DockAnchor.TOP) {
                                jsp.setDividerLocation(jsp.getMinimumDividerLocation());
                            }else{
                                jsp.setDividerLocation(jsp.getMaximumDividerLocation());
                            }
                        }
                        repaint();
                    }
                }
            }
        });
        bar.add(t);
        windows.add(tb);
        if (selected == null) {
            selected = tb;
            t.setSelected(true);
            pane.add(selected.win, BorderLayout.CENTER);
        }
        bar.setFloatable(false);
    }

    public void setSelectedToolAt(int index) {
        JDockToolAndButton a = windows.get(index);
        a.button.setSelected(true);
    }

    public void setToolBarVisible(boolean b) {
        bar.setVisible(b);
    }
}

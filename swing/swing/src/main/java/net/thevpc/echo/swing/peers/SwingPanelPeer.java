package net.thevpc.echo.swing.peers;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppPanel;
import net.thevpc.echo.constraints.*;
import net.thevpc.echo.spi.peers.AppComponentPeer;
import net.thevpc.echo.spi.peers.AppPanelPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SwingPanelPeer implements SwingPeer, AppPanelPeer {
    private JPanel swingComponent;
    private JPanel holder;
    private AppPanel appPanel;

    public SwingPanelPeer() {
    }

    public void install(AppComponent component) {
        if (swingComponent != null) {
            return;
        }
        this.appPanel = (AppPanel) component;
        this.swingComponent = new JPanel();

        this.swingComponent.putClientProperty(AppComponentPeer.class.getName(),this);
        this.swingComponent.putClientProperty(AppComponent.class.getName(),appPanel);

        SwingPeerHelper.installComponent(this.appPanel,swingComponent);
        holder = swingComponent;
        this.swingComponent.setName("SwingPanelPeer.Panel");
        this.appPanel.parentConstraints().onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                if(appPanel.children().size()>0){
                    System.err.println("<ERROR>");
                }
                relayout(true);
            }
        });
        relayout(false);
    }

    public void addChild(AppComponent child, int index) {
        Component childComponent = (Component) child.peer().toolkitComponent();
        switch (resolveLayout()) {
            case BORDER: {
                Anchor a = child.anchor().get();
                if (a == null) {
                    a = Anchor.CENTER;
                }
                switch (a) {
                    case CENTER: {
                        holder.add(childComponent, BorderLayout.CENTER);
                        break;
                    }
                    case TOP: {
                        holder.add(childComponent, BorderLayout.PAGE_START);
                        break;
                    }
                    case BOTTOM: {
                        holder.add(childComponent, BorderLayout.PAGE_END);
                        break;
                    }
                    case LEFT: {
                        holder.add(childComponent, BorderLayout.LINE_START);
                        break;
                    }
                    case RIGHT: {
                        holder.add(childComponent, BorderLayout.LINE_END);
                        break;
                    }
                }
                break;
            }
            case FLOW:
            case HORIZONTAL: {
                applyAnchorAsAlignment(child, childComponent, Anchor.TOP_LEFT);
                holder.add(childComponent);
                break;
            }
            case VERTICAL: {
                applyAnchorAsAlignment(child, childComponent, Anchor.TOP_LEFT);
                holder.add(childComponent);
                break;
            }
            case GRID: {
                GridBagConstraints g = new GridBagConstraints();
                Pos pos = child.childConstraints().get(Pos.class);
                if (pos == null) {
                    pos = new Pos(0, 0);
                }
                Anchor a = child.anchor().get();
                if (a == null) {
                    a = Anchor.CENTER;
                }
                switch (a) {
                    case CENTER: {
                        g.anchor = GridBagConstraints.CENTER;
                        break;
                    }
                    case TOP: {
                        g.anchor = GridBagConstraints.PAGE_START;
                        break;
                    }
                    case BOTTOM: {
                        g.anchor = GridBagConstraints.PAGE_END;
                        break;
                    }
                    case RIGHT: {
                        g.anchor = GridBagConstraints.LINE_END;
                        break;
                    }
                    case LEFT: {
                        g.anchor = GridBagConstraints.LINE_START;
                        break;
                    }
                    case BOTTOM_LEFT: {
                        g.anchor = GridBagConstraints.SOUTHWEST;
                        break;
                    }
                    case BOTTOM_RIGHT: {
                        g.anchor = GridBagConstraints.SOUTHEAST;
                        break;
                    }
                    case TOP_RIGHT: {
                        g.anchor = GridBagConstraints.NORTHEAST;
                        break;
                    }
                    case TOP_LEFT: {
                        g.anchor = GridBagConstraints.NORTHWEST;
                        break;
                    }
                }
                Span span = child.childConstraints().get(Span.class);
                g.gridx = pos.getX();
                g.gridy = pos.getY();
                if (span != null) {
                    g.gridwidth = pos.getX();
                    g.gridheight = pos.getY();
                }
                Weight weight = child.childConstraints().get(Weight.class);
                if (weight != null) {
                    g.weightx = weight.getX();
                    g.weighty = weight.getY();
                }
                GrowX growX = child.childConstraints().get(GrowX.class);
                GrowX growY = child.childConstraints().get(GrowX.class);
                if (growX == null) {
                    growX = GrowX.NEVER;
                }
                if (growY == null) {
                    growY = GrowX.NEVER;
                }
                switch (growX) {
                    case NEVER: {
                        switch (growY) {
                            case NEVER: {
                                g.fill = GridBagConstraints.NONE;
                                break;
                            }
                            case ALWAYS: {
                                g.fill = GridBagConstraints.VERTICAL;
                                break;
                            }
                            case SOMETIMES: {
                                g.fill = GridBagConstraints.VERTICAL;
                                break;
                            }
                        }
                        break;
                    }
                    case ALWAYS: {
                        switch (growY) {
                            case NEVER: {
                                g.fill = GridBagConstraints.HORIZONTAL;
                                break;
                            }
                            case ALWAYS: {
                                g.fill = GridBagConstraints.BOTH;
                                break;
                            }
                            case SOMETIMES: {
                                g.fill = GridBagConstraints.BOTH;
                                break;
                            }
                        }
                        break;
                    }
                    case SOMETIMES: {
                        switch (growY) {
                            case NEVER: {
                                g.fill = GridBagConstraints.HORIZONTAL;
                                break;
                            }
                            case ALWAYS: {
                                g.fill = GridBagConstraints.BOTH;
                                break;
                            }
                            case SOMETIMES: {
                                g.fill = GridBagConstraints.BOTH;
                                break;
                            }
                        }
                        break;
                    }
                }
                Padding padding = child.childConstraints().get(Padding.class);
                if (padding != null) {
                    g.ipadx = (int) padding.getWidth();
                    g.ipady = (int) padding.getHeight();
                }
                Margin insets = child.childConstraints().get(Margin.class);
                if (insets != null) {
                    g.insets = new Insets((int) insets.getTop(), (int) insets.getLeft(), (int) insets.getBottom(), (int) insets.getRight());
                } else {
                    ParentPadding ppadding = appPanel.parentConstraints().get(ParentPadding.class);
                    if (ppadding != null) {
                        g.insets = new Insets((int) ppadding.getTop(), (int) ppadding.getLeft(), (int) ppadding.getBottom(), (int) ppadding.getRight());
                    }
                }
                holder.add(childComponent, g);
            }
        }
        refreshPanel();
    }

    private void refreshPanel() {
        SwingHelpers.refreshPanel(swingComponent,2);
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        Component comp = (Component) other.peer().toolkitComponent();
        Component[] old = holder.getComponents();
        int oldIndex = Arrays.asList(old).indexOf(comp);
        if(oldIndex<0){
            System.err.println("<WHY>");
            //workaround
            if (old.length==1 && index==0){
                comp=old[0];
            }
        }
//        holder.
        holder.remove(comp);
        refreshPanel();
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

    private void applyAnchorAsAlignment(AppComponent child, Component childComponent, Anchor defaultAnchor) {
        Anchor a = child.anchor().get();
        if (a == null) {
            a = defaultAnchor;
        }
        if (a != null) {
            if (childComponent instanceof JComponent) {
                JComponent cc = (JComponent) childComponent;
                switch (a) {
                    case LEFT: {
                        cc.setAlignmentX(Component.LEFT_ALIGNMENT);
                        cc.setAlignmentY(Component.CENTER_ALIGNMENT);
                        break;
                    }
                    case CENTER: {
                        cc.setAlignmentX(Component.CENTER_ALIGNMENT);
                        cc.setAlignmentY(Component.CENTER_ALIGNMENT);
                        break;
                    }
                    case TOP: {
                        cc.setAlignmentX(Component.CENTER_ALIGNMENT);
                        cc.setAlignmentY(Component.TOP_ALIGNMENT);
                        break;
                    }
                    case BOTTOM: {
                        cc.setAlignmentX(Component.CENTER_ALIGNMENT);
                        cc.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                        break;
                    }
                    case RIGHT: {
                        cc.setAlignmentX(Component.RIGHT_ALIGNMENT);
                        cc.setAlignmentY(Component.CENTER_ALIGNMENT);
                        break;
                    }
                    case BOTTOM_LEFT: {
                        cc.setAlignmentX(Component.LEFT_ALIGNMENT);
                        cc.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                        break;
                    }
                    case BOTTOM_RIGHT: {
                        cc.setAlignmentX(Component.RIGHT_ALIGNMENT);
                        cc.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                        break;
                    }
                    case TOP_LEFT: {
                        cc.setAlignmentX(Component.LEFT_ALIGNMENT);
                        cc.setAlignmentY(Component.TOP_ALIGNMENT);
                        break;
                    }
                    case TOP_RIGHT: {
                        cc.setAlignmentX(Component.RIGHT_ALIGNMENT);
                        cc.setAlignmentY(Component.TOP_ALIGNMENT);
                        break;
                    }
                }
            }
        }
    }

    protected void relayout(boolean rebuild) {
        Layout layout = resolveLayout();
        GrowX gx = appPanel.parentConstraints().get(GrowX.class);
        GrowY gy = appPanel.parentConstraints().get(GrowY.class);
        ParentWrapCount w = appPanel.parentConstraints().get(ParentWrapCount.class);
        ParentPadding padding = appPanel.parentConstraints().get(ParentPadding.class);
        int wrap = w == null ? 0 : w.getCount();
        switch (layout) {
            case BORDER: {
                applyParentGrowXY(gx, gy);
                holder.setLayout(new BorderLayout());
                break;
            }
            case FLOW: {
                int vgap = 5;
                int hgap = 5;
                if (padding != null) {
                    vgap = (int) (padding.getTop() + padding.getBottom());
                    hgap = (int) (padding.getLeft() + padding.getRight());
                }
                applyParentGrowXY(gx, gy);
                holder.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
                break;
            }
            case VERTICAL: {
                int vgap = 5;
                int hgap = 5;
                if (padding != null) {
                    vgap = (int) (padding.getTop() + padding.getBottom());
                    hgap = (int) (padding.getLeft() + padding.getRight());
                }
                applyParentGrowXY(gx, gy);
                if (wrap > 1) {
                    holder.setLayout(new GridLayout(0, wrap, hgap, vgap));
                } else {
                    BoxLayout mgr = new BoxLayout(holder, BoxLayout.Y_AXIS);
                    //mgr.
                    holder.setLayout(mgr);
                }
                break;
            }
            case HORIZONTAL: {
                int vgap = 5;
                int hgap = 5;
                if (padding != null) {
                    vgap = (int) (padding.getTop() + padding.getBottom());
                    hgap = (int) (padding.getLeft() + padding.getRight());
                }
                applyParentGrowXY(gx, gy);
                if (wrap > 1) {
                    holder.setLayout(new GridLayout(wrap, 0, hgap, vgap));
                } else {
                    holder.setLayout(new BoxLayout(holder, BoxLayout.X_AXIS));
                }
                break;
            }
            case GRID: {
                applyParentGrowXY(gx, gy);
                holder.setLayout(new GridBagLayout());
                break;
            }
            case STACK: {
                throw new UnsupportedOperationException("not supported yet");
            }
        }
        if (rebuild) {
            int index = 0;
            holder.removeAll();
            for (AppComponent child : appPanel.children()) {
                addChild(child, index);
                index++;
            }
        }
//        for (AppComponent child : panel.children()) {
//
//        }
    }

    private void applyParentGrowXY(GrowX gx, GrowY gy) {
        if ((gx != null && (gx != GrowX.NEVER)) && (gy != null && (gy != GrowY.NEVER))) {
            swingComponent.removeAll();
            swingComponent.setLayout(new BorderLayout());
            JPanel p1 = new JPanel(new BorderLayout());
            p1.setName("FORCE_ALIGN_LEFT");
            swingComponent.add(p1, BorderLayout.LINE_START);
//            jcomponent.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            JPanel p2 = new JPanel();
            p1.add(p2, BorderLayout.PAGE_START);
            p2.setName("FORCE_ALIGN_TOP");
//            p1.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            holder = p2;
        } else if ((gx != null && (gx != GrowX.NEVER))) {
            swingComponent.removeAll();
            swingComponent.setLayout(new BorderLayout());
            JPanel p1 = new JPanel(new BorderLayout());
            p1.setName("FORCE_ALIGN_LEFT");
            swingComponent.add(p1, BorderLayout.PAGE_START);
//            jcomponent.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            holder = p1;
        } else if ((gy != null && (gy != GrowY.NEVER))) {
            swingComponent.removeAll();
            swingComponent.setLayout(new BorderLayout());
            JPanel p2 = new JPanel();
            swingComponent.add(p2, BorderLayout.LINE_START);
            p2.setName("FORCE_ALIGN_TOP");
//            jcomponent.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            holder = p2;
        } else {
            holder = swingComponent;
        }
    }

    public Layout resolveLayout() {
        Layout pl = appPanel.parentConstraints().get(Layout.class);
        if (pl == null) {
            //guess
            return Layout.FLOW;
        }
        return pl;
    }

    @Override
    public Component awtComponent() {
        return (Component) toolkitComponent();
    }

    @Override
    public JComponent jcomponent() {
        return (JComponent) toolkitComponent();
    }
}

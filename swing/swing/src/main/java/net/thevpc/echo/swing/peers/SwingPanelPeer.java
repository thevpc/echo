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
    private Pos lastPos = new Pos(0, 0);

    public SwingPanelPeer() {
    }

    public void install(AppComponent component) {
        if (swingComponent != null) {
            return;
        }
        this.appPanel = (AppPanel) component;
        this.swingComponent = new JPanel();

        this.swingComponent.putClientProperty(AppComponentPeer.class.getName(), this);
        this.swingComponent.putClientProperty(AppComponent.class.getName(), appPanel);

        SwingPeerHelper.installComponent(this.appPanel, swingComponent);
        holder = swingComponent;
        this.swingComponent.setName("SwingPanelPeer.Panel");
        this.appPanel.parentConstraints().onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
//                if (appPanel.children().size() > 0) {
//                    System.err.println("<ERROR>");
//                }
                relayout(true);
            }
        });
        relayout(false);
    }

    @Override
    public void addChild(AppComponent child, int index) {
        Component childComponent = (Component) child.peer().toolkitComponent();
        Layout layout = resolveLayout();
        switch (layout) {
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
            case FLOW: {
                applyAnchorAsAlignment(child, childComponent, Anchor.TOP_LEFT);
                holder.add(childComponent);
                break;
            }
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
                ColumnCount pwc = appPanel.parentConstraints().get(ColumnCount.class);
                int pwcc = pwc != null ? pwc.getCount() : 0;
                GridBagConstraints g = new GridBagConstraints();
                Pos pos = child.childConstraints().get(Pos.class);
                Anchor a = child.anchor().get();
                if (a == null) {
                    AllAnchors aa = appPanel.parentConstraints().get(AllAnchors.class);
                    if (aa != null) {
                        a = Anchor.valueOf(aa.name());
                    }
                    if (a == null) {
                        a = Anchor.CENTER;
                    }
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
                NewRow newRow = child.childConstraints().get(NewRow.class);
                if (pos != null) {
                    g.gridx = pos.getX();
                    g.gridy = pos.getY();
                } else {
                    g.gridx = lastPos.getX();
                    g.gridy = lastPos.getY();
                    if (newRow == NewRow.BEFORE) {
                        g.gridy++;
                    }
                }
                Span span = child.childConstraints().get(Span.class);
                if (span != null) {
                    g.gridwidth = span.getColSpan();
                    g.gridheight = span.getRowSpan();
                }
                lastPos = new Pos(g.gridx + g.gridwidth, g.gridy);
                if (lastPos.getX() == pwcc) {
                    lastPos = new Pos(0, g.gridy + 1);
                } else if (newRow == NewRow.AFTER) {
                    lastPos = new Pos(0, g.gridy + 1);
                }
                Grow gr = child.childConstraints().get(Grow.class);
                if (gr == null) {
                    AllGrow pg = appPanel.parentConstraints().get(AllGrow.class);
                    if(pg!=null){
                        gr=Grow.valueOf(pg.name());
                    }
                }
                Weight weight = child.childConstraints().get(Weight.class);
                if (weight != null) {
                    g.weightx = weight.getX();
                    g.weighty = weight.getY();
                    if (gr != null) {
                        if (g.weightx == 0) {
                            g.weightx = (gr == Grow.BOTH || gr == Grow.HORIZONTAL) ? 1 : 0;
                        }
                        if (g.weighty == 0) {
                            g.weighty = (gr == Grow.BOTH || gr == Grow.VERTICAL) ? 1 : 0;
                        }
                    }
                } else {
                    if (gr != null) {
                        g.weightx = (gr == Grow.BOTH || gr == Grow.HORIZONTAL) ? 1 : 0;
                        g.weighty = (gr == Grow.BOTH || gr == Grow.VERTICAL) ? 1 : 0;
                    }
                }
                Fill fill = child.childConstraints().get(Fill.class);
                if (fill == null) {
                    AllFill ppadding = appPanel.parentConstraints().get(AllFill.class);
                    if (ppadding != null) {
                        fill = Fill.valueOf(ppadding.name());
                    }
                    if (fill == null) {
                        fill = Fill.NONE;
                    }
                }
                switch (fill) {
                    case NONE: {
                        g.fill = GridBagConstraints.NONE;
                        break;
                    }
                    case HORIZONTAL: {
                        g.fill = GridBagConstraints.HORIZONTAL;
                        break;
                    }
                    case VERTICAL: {
                        g.fill = GridBagConstraints.VERTICAL;
                        break;
                    }
                    case BOTH: {
                        g.fill = GridBagConstraints.BOTH;
                        break;
                    }
                }
                Padding padding = child.childConstraints().get(Padding.class);
                if (padding != null) {
                    g.ipadx = (int) padding.getWidth();
                    g.ipady = (int) padding.getHeight();
                } else {
                    AllPaddings ppadding = appPanel.parentConstraints().get(AllPaddings.class);
                    if (ppadding != null) {
                        g.ipadx = (int) ppadding.getWidth();
                        g.ipady = (int) ppadding.getHeight();
                    }
                }
                ParentMargin pmargin = appPanel.parentConstraints().get(ParentMargin.class);
                if (pmargin == null) {
                    pmargin = new ParentMargin(0, 0, 0, 0);
                } else {
                    pmargin = new ParentMargin(
                            g.gridy == 0 ? pmargin.getTop() : 0,
                            g.gridx == 0 ? pmargin.getLeft() : 0,
                            0,
                            g.gridy + g.gridwidth == pwcc ? pmargin.getRight() : 0
                    );
                }
                Margin insets = child.childConstraints().get(Margin.class);
                if (insets != null) {
                    g.insets = new Insets(
                            (int) (insets.getTop() + pmargin.getTop()),
                            (int) (insets.getLeft() + pmargin.getLeft()),
                            (int) (insets.getBottom() + pmargin.getBottom()),
                            (int) (insets.getRight() + pmargin.getRight())
                    );
                } else {
                    AllMargins pinsets = appPanel.parentConstraints().get(AllMargins.class);
                    if (pinsets != null) {
                        g.insets = new Insets(
                                (int) (pinsets.getTop() + pmargin.getTop()),
                                (int) (pinsets.getLeft() + pmargin.getLeft()),
                                (int) (pinsets.getBottom() + pmargin.getBottom()),
                                (int) (pinsets.getRight() + pmargin.getRight())
                        );
                    } else {
                        g.insets = new Insets(
                                (int) (pmargin.getTop()),
                                (int) (pmargin.getLeft()),
                                (int) (pmargin.getBottom()),
                                (int) (pmargin.getRight())
                        );
                    }
                }
                holder.add(childComponent, g);
            }
        }
        refreshPanel();
    }

    private void refreshPanel() {
        SwingHelpers.refreshPanel(swingComponent, 2);
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        Component comp = (Component) other.peer().toolkitComponent();
//        Component[] old = holder.getComponents();
//        int oldIndex = Arrays.asList(old).indexOf(comp);
//        if (oldIndex < 0) {
//            System.err.println("<WHY>");
//            //workaround
//            if (old.length == 1 && index == 0) {
//                comp = old[0];
//            }
//        }
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
        GrowContainer growContainer = appPanel.parentConstraints().get(GrowContainer.class);
        ColumnCount w = appPanel.parentConstraints().get(ColumnCount.class);
        AllPaddings padding = appPanel.parentConstraints().get(AllPaddings.class);
        int wrap = w == null ? 0 : w.getCount();
        switch (layout) {
            case BORDER: {
                applyParentGrowContainerDefaultIsGrowing(growContainer);
                holder.setLayout(new BorderLayout());
                break;
            }
            case FLOW: {
                int vgap = 5;
                int hgap = 5;
                if (padding != null) {
                    vgap = (int) padding.getHeight();
                    hgap = (int) padding.getWidth();
                }
                applyParentGrowContainerDefaultIsGrowing(growContainer);
                holder.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
                break;
            }
            case VERTICAL: {
                int vgap = 5;
                int hgap = 5;
                if (padding != null) {
                    vgap = (int) padding.getHeight();
                    hgap = (int) padding.getWidth();
                }
                applyParentGrowContainerDefaultIsGrowing(growContainer);
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
                    vgap = (int) padding.getHeight();
                    hgap = (int) padding.getWidth();
                }
                applyParentGrowContainerDefaultIsGrowing(growContainer);
                if (wrap > 1) {
                    holder.setLayout(new GridLayout(wrap, 0, hgap, vgap));
                } else {
                    holder.setLayout(new BoxLayout(holder, BoxLayout.X_AXIS));
                }
                break;
            }
            case GRID: {
                applyParentGrowContainerDefaultNonGrowing(growContainer);
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
            lastPos = new Pos(0, 0);
            for (AppComponent child : appPanel.children()) {
                addChild(child, index);
                index++;
            }
        }
//        for (AppComponent child : panel.children()) {
//
//        }
    }

    private void applyParentGrowContainerDefaultIsGrowing(GrowContainer gx) {
        if (gx == GrowContainer.BOTH) {
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
        } else if (gx == GrowContainer.HORIZONTAL) {
            swingComponent.removeAll();
            swingComponent.setLayout(new BorderLayout());
            JPanel p1 = new JPanel(new BorderLayout());
            p1.setName("FORCE_ALIGN_LEFT");
            swingComponent.add(p1, BorderLayout.PAGE_START);
//            jcomponent.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            holder = p1;
        } else if (gx == GrowContainer.VERTICAL) {
            swingComponent.removeAll();
            swingComponent.setLayout(new BorderLayout());
            JPanel p2 = new JPanel();
            p2.setName("ALIGNED_HOLDER");
            swingComponent.add(p2, BorderLayout.LINE_START);
            swingComponent.setName("FORCE_ALIGN_TOP");
//            jcomponent.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            holder = p2;
        } else {
            holder = swingComponent;
        }
    }

    private void applyParentGrowContainerDefaultNonGrowing(GrowContainer gx) {
        if (gx == GrowContainer.BOTH) {
            holder = swingComponent;
        } else if (gx == GrowContainer.VERTICAL) {
            swingComponent.removeAll();
            swingComponent.setLayout(new BorderLayout());
            JPanel p1 = new JPanel(new BorderLayout());
            p1.setName("FORCE_ALIGN_LEFT");
            swingComponent.add(p1, BorderLayout.LINE_START);
//            jcomponent.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            holder = p1;
        } else if (gx == GrowContainer.HORIZONTAL) {
            swingComponent.removeAll();
            swingComponent.setLayout(new BorderLayout());
            JPanel p2 = new JPanel();
            swingComponent.add(p2, BorderLayout.PAGE_START);
            p2.setName("FORCE_ALIGN_TOP");
//            jcomponent.add(new JLabel("NO_CENTER"), BorderLayout.CENTER);
            holder = p2;
        } else {
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

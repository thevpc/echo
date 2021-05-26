package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.spi.peers.AppChoiceListPeer;
import net.thevpc.echo.swing.SwingApplicationUtils;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.thevpc.echo.Application;

public class SwingChoiceListPeer implements SwingPeer, AppChoiceListPeer {
    private JList swingComponent;
    private AppChoiceList<?> component;
    private DefaultListModel dataModel;

    public SwingChoiceListPeer() {
    }

    public void install(AppComponent component0) {
        this.component = (AppChoiceList<?>) component0;
        DefaultListModel<Object> dataModel = new DefaultListModel<>();
        swingComponent = new JList(dataModel);
        Layout layout = component.parentConstraints().get(Layout.class);
        if (layout != null) {
            switch (layout) {
                case FLOW:
                case HORIZONTAL: {
                    swingComponent.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                    break;
                }
                case VERTICAL: {
                    swingComponent.setLayoutOrientation(JList.VERTICAL_WRAP);
                    break;
                }
            }
        }
        for (Object value : component.values()) {
            dataModel.addElement(value);
        }
        component.values().onChange(e -> {
            switch (e.eventType()) {
                case ADD: {
                    dataModel.add(e.index(), e.newValue());
                    break;
                }
                case REMOVE: {
                    dataModel.remove(e.index());
                    break;
                }
                case UPDATE: {
                    dataModel.set(e.index(), e.newValue());
                    break;
                }
            }
        });
        for (Integer index : component.selection().indices()) {
            swingComponent.getSelectionModel().addSelectionInterval(index, index);
        }
        swingComponent.addListSelectionListener(new ListSelectionListener() {
            boolean adjusting = false;

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!adjusting) {
                    adjusting = true;
                    try {
                        int[] selectedIndices = swingComponent.getSelectedIndices();
                        List<Integer> all = Arrays.stream(selectedIndices).boxed().collect(Collectors.toList());
                        component.selection().indices().setAll(
                                all.toArray(new Integer[0])
                        );
                    } finally {
                        adjusting = false;
                    }
                }
            }
        });
        component.selection().indices().onChange(e -> {
            int[] indices = component.selection().indices().stream().mapToInt(x -> x.intValue()).toArray();
            swingComponent.setSelectedIndices(indices);
        });
        swingComponent.setCellRenderer(new MyDefaultListCellRenderer(this));
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

    @Override
    public void ensureIndexIsVisible(int index) {
        swingComponent.ensureIndexIsVisible(index);
    }

    private static class MyDefaultListCellRenderer extends DefaultListCellRenderer {
        SwingChoiceListPeer peer;
        AppColor initialForeground;
        AppColor initialBackground;
        AppFont initialFont;
        boolean initialUnderline;
        boolean initialStrikeThrough;

        public MyDefaultListCellRenderer(SwingChoiceListPeer peer) {
            this.peer = peer;
        }

        public Component getListCellRendererComponent0(DefaultChoiceListItemContext c) {
            super.getListCellRendererComponent(c.list, c.text, c.index, c.isSelected, c.cellHasFocus);
            setIcon(c.icon);
            if (c.disabled && !c.isSelected && c.list.hasFocus()) {
                setForeground(c.list.getSelectionBackground());
                setBackground(c.list.getSelectionForeground());
            }
            return this;
        }

        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            boolean disabled = peer.component.disabledSelection().indices().contains(index);
            DefaultChoiceListItemContext c = new DefaultChoiceListItemContext(
                    this, list, index, value, isSelected, cellHasFocus, disabled,
                    value == null ? "" : value.toString(),null
            );
            AppChoiceItemRenderer<?> r = peer.component.itemRenderer().get();
            if (r != null) {
                r.render(c);
            } else {
                getListCellRendererComponent0(c);
            }
            return this;
        }

    }

    private static class DefaultChoiceListItemContext implements AppChoiceItemContext {
        private final JList<?> list;
        private final MyDefaultListCellRenderer myDefaultListCellRenderer;
        private int index;
        private Object value;
        private String text;
        private boolean isSelected;
        private boolean cellHasFocus;
        private boolean disabled;
        private Icon icon;

        public DefaultChoiceListItemContext(MyDefaultListCellRenderer myDefaultListCellRenderer, JList<?> list,
                                      int index, Object value, boolean isSelected,
                                      boolean cellHasFocus, boolean disabled, String text,Icon icon) {
            this.list = list;
            this.index = index;
            this.value = value;
            this.isSelected = isSelected;
            this.cellHasFocus = cellHasFocus;
            this.disabled = disabled;
            this.myDefaultListCellRenderer = myDefaultListCellRenderer;
            this.text = text;
            this.icon = icon;
        }

        @Override
        public AppChoiceControl getChoice() {
            return myDefaultListCellRenderer.peer.component;
        }

        @Override
        public Application getApplication() {
            return myDefaultListCellRenderer.peer.component.app();
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public void setText(String text) {
            this.text = text;
            myDefaultListCellRenderer.setText(text);
        }

        @Override
        public void setOpaque(boolean opaque) {
            myDefaultListCellRenderer.setOpaque(opaque);
        }

        @Override
        public void setTextColor(AppColor color) {
            myDefaultListCellRenderer.setForeground(
                    color == null ? null : (Color) color.peer().toolkitColor()
            );
        }

        @Override
        public void setTextFont(AppFont font) {
            if (font != null) {
                SwingApplicationUtils.setComponentFont(myDefaultListCellRenderer,
                        font, null, null, myDefaultListCellRenderer.initialUnderline, myDefaultListCellRenderer.initialStrikeThrough);
            }
        }

        @Override
        public void setTextUnderline(boolean underline) {
            myDefaultListCellRenderer.initialUnderline = underline;
            SwingApplicationUtils.setComponentFont(myDefaultListCellRenderer,
                    (AppFont) null, null, null, myDefaultListCellRenderer.initialUnderline, null);
        }

        @Override
        public void setTextStrikeThrough(boolean strikeThrough) {
            myDefaultListCellRenderer.initialStrikeThrough = strikeThrough;
            SwingApplicationUtils.setComponentFont(myDefaultListCellRenderer,
                    (AppFont) null, null, null, null, strikeThrough);
        }

        @Override
        public void setTextStrokeSize(int size) {
            SwingApplicationUtils.setComponentTextStrokeSize(myDefaultListCellRenderer, size);
        }

        @Override
        public void setTextAlign(Anchor align) {
            SwingApplicationUtils.setLabelTextAlign(myDefaultListCellRenderer, align);
        }

        @Override
        public void setIcon(AppImage icon) {
            this.icon=SwingHelpers.toAwtIcon(icon);
            myDefaultListCellRenderer.setIcon(
                    this.icon
            );
        }

        @Override
        public boolean isSelected() {
            return isSelected;
        }

        @Override
        public boolean isFocused() {
            return cellHasFocus;
        }

        @Override
        public AppFont getFont() {
            return null;
        }

        @Override
        public AppColor getColor() {
            return null;
        }

        @Override
        public AppColor getBackgroundColor() {
            return null;
        }

        @Override
        public void setBackgroundColor(AppColor color) {
            myDefaultListCellRenderer.setForeground(
                    color == null ? null : (Color) color.peer().toolkitColor()
            );
        }

        @Override
        public void renderDefault() {
            myDefaultListCellRenderer.getListCellRendererComponent0(this);
        }
    }
}

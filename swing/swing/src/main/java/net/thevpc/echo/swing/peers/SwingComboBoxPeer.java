package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.spi.peers.AppComboBoxPeer;
import net.thevpc.echo.swing.SwingApplicationUtils;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import net.thevpc.echo.Application;

public class SwingComboBoxPeer implements SwingPeer, AppComboBoxPeer {
    private JComboBox swingComponent;
    private AppComboBox<Object> appComponent;

    public SwingComboBoxPeer() {
    }

    public void install(AppComponent component0) {
        this.appComponent = (AppComboBox) component0;
        DefaultComboBoxModel dataModel = new DefaultComboBoxModel();
        swingComponent = new JComboBox(dataModel);
        SwingPeerHelper.installComponent(appComponent,swingComponent);
        for (Object value : appComponent.values()) {
            dataModel.addElement(value);
        }
        appComponent.editable().onChangeAndInit(
                ()->swingComponent.setEditable(appComponent.editable().get())
        );
        appComponent.values().onChange(e -> {
            switch (e.eventType()) {
                case ADD: {
                    dataModel.insertElementAt(e.newValue(),e.index());
                    break;
                }
                case REMOVE: {
                    dataModel.removeElementAt(e.index());
                    break;
                }
                case UPDATE: {
                    dataModel.removeElementAt(e.index());
                    dataModel.insertElementAt(e.newValue(),e.index());
                    break;
                }
            }
        });
        Integer i = appComponent.selection().indices().get();
        swingComponent.setSelectedIndex(i == null ? -1 : i);
        swingComponent.addItemListener(new ItemListener() {
            boolean adjusting = false;

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (!adjusting) {
                        try {
                            adjusting = true;
                            Object o = e.getItem();
                            appComponent.selection().set(o);
                        } finally {
                            adjusting = false;
                        }
                    }
                }
            }
        });
        appComponent.selection().indices().onChange(x->{
            Integer index = appComponent.selection().indices().get();
            swingComponent.setSelectedIndex(index == null ? -1 : index);
        });
        swingComponent.setRenderer(new MyDefaultListCellRenderer(this));
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

    private static class MyDefaultListCellRenderer extends BasicComboBoxRenderer {
        SwingComboBoxPeer peer;
        AppColor initialForeground;
        AppColor initialBackground;
        AppFont initialFont;
        boolean initialUnderline;
        boolean initialStrikeThrough;

        public MyDefaultListCellRenderer(SwingComboBoxPeer peer) {
            this.peer = peer;
        }

        public void getListCellRendererComponent0(DefaultComboBoxItemContext c) {
            super.getListCellRendererComponent(c.list, c.value, c.index, c.isSelected, c.cellHasFocus);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            AppChoiceItemRenderer<?> r = peer.appComponent.itemRenderer().get();
            DefaultComboBoxItemContext context = new DefaultComboBoxItemContext(this, list, index, value, isSelected, cellHasFocus);
            if (r != null) {
                r.render(context);
            } else {
                context.renderDefault();
            }
            return this;
        }

    }

    private static class DefaultComboBoxItemContext implements AppChoiceItemContext {
        private final MyDefaultListCellRenderer myDefaultListCellRenderer;
        private final JList list;
        private final int index;
        private Object value;
        private boolean isSelected;
        private boolean cellHasFocus;

        public DefaultComboBoxItemContext(MyDefaultListCellRenderer myDefaultListCellRenderer, JList list, int index, Object value, boolean isSelected, boolean cellHasFocus) {
            this.index = index;
            this.value = value;
            this.list = list;
            this.isSelected = isSelected;
            this.cellHasFocus = cellHasFocus;
            this.myDefaultListCellRenderer = myDefaultListCellRenderer;
        }

        @Override
        public AppChoiceControl getChoice() {
            return myDefaultListCellRenderer.peer.appComponent;
        }

        @Override
        public Application getApplication() {
            return myDefaultListCellRenderer.peer.appComponent.app();
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
            myDefaultListCellRenderer.setIcon(
                    SwingHelpers.toAwtIcon(icon)
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

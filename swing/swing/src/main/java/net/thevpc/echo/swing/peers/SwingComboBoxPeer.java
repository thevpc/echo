package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.spi.peers.AppComboBoxPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import net.thevpc.echo.swing.helpers.SwingAppChoiceItemContext1;

public class SwingComboBoxPeer implements SwingPeer, AppComboBoxPeer {

    private JComboBox swingComponent;
    private AppComboBox<Object> appComponent;

    public SwingComboBoxPeer() {
    }

    public void install(AppComponent component0) {
        this.appComponent = (AppComboBox) component0;
        DefaultComboBoxModel dataModel = new DefaultComboBoxModel();
        swingComponent = new JComboBox(dataModel);
        SwingPeerHelper.installComponent(appComponent, swingComponent);
        for (Object value : appComponent.values()) {
            dataModel.addElement(value);
        }
        appComponent.editable().onChangeAndInit(
                () -> swingComponent.setEditable(appComponent.editable().get())
        );
        appComponent.values().onChange(e -> {
            switch (e.eventType()) {
                case ADD: {
                    dataModel.insertElementAt(e.newValue(), e.index());
                    break;
                }
                case REMOVE: {
                    dataModel.removeElementAt(e.index());
                    break;
                }
                case UPDATE: {
                    dataModel.removeElementAt(e.index());
                    dataModel.insertElementAt(e.newValue(), e.index());
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
        appComponent.selection().indices().onChange(x -> {
            Integer index = appComponent.selection().indices().get();
            swingComponent.setSelectedIndex(index == null ? -1 : index);
        });
        swingComponent.setRenderer(new MyDefaultListCellRenderer(this));
        ComboBoxEditor editor = swingComponent.getEditor();
        Component editorComponent = editor.getEditorComponent();
        if (editorComponent instanceof JTextComponent) {
            SwingPeerHelper.installTextComponent(appComponent, (JTextComponent) editorComponent);
        }
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

    @Override
    public void replaceSelection(String newValue) {
        Component ed = swingComponent.getEditor().getEditorComponent();
        if (ed instanceof JTextComponent) {
            ((JTextComponent) ed).replaceSelection(newValue);
        }
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
            JList list = c.list;
            super.getListCellRendererComponent(list, c.getText(), c.getIndex(), c.isSelected(), c.isFocused());
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            AppChoiceItemRenderer<?> r = peer.appComponent.itemRenderer().get();
            DefaultComboBoxItemContext context = new DefaultComboBoxItemContext(
                    list,
                    peer.appComponent, this, value,
                    index, null,
                    isSelected, cellHasFocus,
                    false
            );
            if (r != null) {
                r.render(context);
            } else {
                context.renderDefault();
            }
            return this;
        }

    }

    private static class DefaultComboBoxItemContext<T> extends SwingAppChoiceItemContext1<T> {

        JList list;

        public DefaultComboBoxItemContext(JList list, AppChoiceControl<T> appChoiceControl, MyDefaultListCellRenderer jcomponent, T value, int index, Icon icon, boolean isSelected, boolean cellHasFocus, boolean disabled) {
            super(appChoiceControl, jcomponent, value, index, icon, isSelected, cellHasFocus, disabled);
            this.list = list;
        }

        @Override
        public void renderDefault() {
            ((MyDefaultListCellRenderer) getJcomponent()).getListCellRendererComponent0(this);
        }
    }
}

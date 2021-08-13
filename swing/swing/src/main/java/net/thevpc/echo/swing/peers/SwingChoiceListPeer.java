package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.spi.peers.AppChoiceListPeer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.thevpc.echo.swing.helpers.SwingAppChoiceItemContext1;

public class SwingChoiceListPeer implements SwingPeer, AppChoiceListPeer {

    private JList swingComponent;
    private AppChoiceList<?> appComponent;
    private DefaultListModel dataModel;

    public SwingChoiceListPeer() {
    }

    public void install(AppComponent component0) {
        this.appComponent = (AppChoiceList<?>) component0;
        DefaultListModel<Object> dataModel = new DefaultListModel<>();
        swingComponent = new JList(dataModel);
        Layout layout = appComponent.parentConstraints().get(Layout.class);
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
        for (Object value : appComponent.values()) {
            dataModel.addElement(value);
        }
        appComponent.values().onChange(e -> {
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
        for (Integer index : appComponent.selection().indices()) {
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
                        appComponent.selection().indices().setAll(
                                all.toArray(new Integer[0])
                        );
                    } finally {
                        adjusting = false;
                    }
                }
            }
        });
        appComponent.selection().indices().onChange(e -> {
            int[] indices = appComponent.selection().indices().stream().mapToInt(x -> x.intValue()).toArray();
            swingComponent.setSelectedIndices(indices);
        });
        swingComponent.setCellRenderer(new MyDefaultListCellRenderer(this));
        appComponent.onChange(e->{
            if("UpdateUI".equals(e.changeId())){
                swingComponent.invalidate();
                swingComponent.revalidate();
                swingComponent.repaint();
                swingComponent.setCellRenderer(new MyDefaultListCellRenderer(this));
            }
        });
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
            JList list = c.list;
            super.getListCellRendererComponent(list, c.getText(), c.getIndex(), c.isSelected(), c.isFocused());
            setIcon(c.getIcon());
            if (c.isDisabled() /*&& !c.isSelected() && list.hasFocus()*/) {
                setBackground(new JLabel().getBackground());
//                setForeground(new JLabel().getDis());
//                setForeground(list.getSelectionBackground());
//                setBackground(list.getSelectionForeground());
            }
            return this;
        }

        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            AppChoiceList component = peer.appComponent;
            Object rValue = component.values().get(index);
            Predicate o = (Predicate) component.selection().disablePredicate().get();
            boolean disabled =
                    (o !=null && o.test(rValue)) ||  component.selection().disabledValues().contains(rValue);
            DefaultChoiceListItemContext c = new DefaultChoiceListItemContext(
                    list,
                    component, this, value,
                    index, null, isSelected, cellHasFocus, disabled);
            AppChoiceItemRenderer r = (AppChoiceItemRenderer) component.itemRenderer().get();
            if (r != null) {
                r.render(c);
            } else {
                getListCellRendererComponent0(c);
            }
            return this;
        }

    }

    private static class DefaultChoiceListItemContext<T> extends SwingAppChoiceItemContext1<T> {

        JList<?> list;

        public DefaultChoiceListItemContext(JList<?> list, AppChoiceControl<T> appChoiceControl, MyDefaultListCellRenderer jcomponent, T value, int index, Icon icon, boolean isSelected, boolean cellHasFocus, boolean disabled) {
            super(appChoiceControl, jcomponent, value, index, icon, isSelected, cellHasFocus, disabled);
            this.list = list;
        }

        @Override
        public void renderDefault() {
            ((MyDefaultListCellRenderer) jcomponent).getListCellRendererComponent0(this);
        }
    }
}

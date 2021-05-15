package net.thevpc.echo.swing.helpers.list;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;

import javax.swing.*;
import net.thevpc.common.props.ObservableList;

public class SwingListComponentModel<T> extends AbstractListModel<T> {
    private ObservableList<T> items;

    public SwingListComponentModel(ObservableList<T> items) {
        super();
        this.items = items;
        items.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                switch (event.getAction()){
                    case ADD:{
                        fireIntervalAdded(SwingListComponentModel.this,event.getIndex(),event.getIndex());
                        break;
                    }
                    case REMOVE:{
                        fireIntervalRemoved(SwingListComponentModel.this,event.getIndex(),event.getIndex());
                        break;
                    }
                    case UPDATE:{
                        fireContentsChanged(SwingListComponentModel.this,event.getIndex(),event.getIndex());
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public T getElementAt(int index) {
        return items.get(index);
    }
}

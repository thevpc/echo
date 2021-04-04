package net.thevpc.echo.swing.core.swing;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;

import javax.swing.*;
import net.thevpc.common.props.ObservableList;

public class PListComponentModel<T> extends AbstractListModel<T> {
    private ObservableList<T> items;

    public PListComponentModel(ObservableList<T> items) {
        super();
        this.items = items;
        items.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                switch (event.getAction()){
                    case ADD:{
                        fireIntervalAdded(PListComponentModel.this,event.getIndex(),event.getIndex());
                        break;
                    }
                    case REMOVE:{
                        fireIntervalRemoved(PListComponentModel.this,event.getIndex(),event.getIndex());
                        break;
                    }
                    case UPDATE:{
                        fireContentsChanged(PListComponentModel.this,event.getIndex(),event.getIndex());
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

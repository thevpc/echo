package net.thevpc.echo.impl.components;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.DisabledSelectionStrategy;
import net.thevpc.common.props.impl.PropertyAdjusterContext;
import net.thevpc.common.props.impl.PropsHelper;
import net.thevpc.common.props.impl.WritableListIndexSelectionImpl;
import net.thevpc.echo.Application;
import net.thevpc.echo.SimpleItem;
import net.thevpc.echo.api.components.AppChoiceControl;
import net.thevpc.echo.api.components.AppChoiceItemRenderer;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.SimpleItemAppChoiceItemRenderer;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class ChoiceBase<T> extends ControlBase implements AppChoiceControl<T> {

    private WritableList<T> values;
    private WritableListIndexSelectionExt<T> selection;
    private PropertyType itemType;
    private WritableValue<AppChoiceItemRenderer<T>> itemRenderer;

    public ChoiceBase(String id, PropertyType itemType, boolean acceptMulti, Application app,
            Class<? extends AppComponent> componentType,
            Class<? extends AppComponentPeer> peerType
    ) {
        super(id, app, componentType, peerType);
        this.itemType = itemType;
        values = Props.of("values").listOf(itemType);
        selection = new WritableListIndexSelectionImpl<>("selection", itemType, values);
        selection.disableSelectionStrategy().set(DisabledSelectionStrategy.SELECT_NEXT);
        propagateEvents(values, selection);

        selection().disablePredicate();
        itemRenderer = Props.of("itemRenderer").valueOf(
                PropertyType.of(AppChoiceItemRenderer.class, itemType()),
                null
        );
        if (itemType().equals(PropertyType.of(SimpleItem.class))) {
            itemRenderer().set(
                    (AppChoiceItemRenderer<T>) new SimpleItemAppChoiceItemRenderer(app())
            );
        }
    }

    @Override
    public WritableValue<AppChoiceItemRenderer<T>> itemRenderer() {
        return itemRenderer;
    }

    @Override
    public WritableListIndexSelectionExt<T> selection() {
        return selection;
    }

    @Override
    public WritableList<T> values() {
        return values;
    }

    @Override
    public PropertyType itemType() {
        return itemType;
    }
}

package net.thevpc.echo.impl.components;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.PropertyAdjusterContext;
import net.thevpc.common.props.impl.WritableListIndexSelectionImpl;
import net.thevpc.echo.Application;
import net.thevpc.echo.SimpleItem;
import net.thevpc.echo.api.components.AppChoiceControl;
import net.thevpc.echo.api.components.AppChoiceItemRenderer;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.SimpleItemAppChoiceItemRenderer;
import net.thevpc.echo.spi.peers.AppComponentPeer;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChoiceBase<T> extends ControlBase implements AppChoiceControl<T> {

    private WritableList<T> values;
    private WritableListIndexSelectionExt<T> selection;
    private WritableListIndexSelection<T> disabledSelection;
    private WritableValue<Predicate<T>> disabledPredicate;
    private WritableBoolean multipleValues = Props.of("multipleValues").booleanOf(false);
    private PropertyType itemType;
    private WritableValue<AppChoiceItemRenderer<T>> itemRenderer;

    public ChoiceBase(String id, PropertyType itemType, boolean acceptMulti, Application app,
            Class<? extends AppComponent> componentType,
            Class<? extends AppComponentPeer> peerType
    ) {
        super(id, app, componentType, peerType);
        this.itemType = itemType;
        values = Props.of("values").listOf(itemType);
        disabledPredicate = Props.of("disabledPredicate").valueOf(
                PropertyType.of(Predicate.class, itemType),
                null);
        selection = new WritableListIndexSelectionImpl<>("selection", itemType, values);
        disabledSelection = new WritableListIndexSelectionImpl<>("disabledSelection", itemType, values);
        disabledPredicate.onChange(e -> revalidateDisabled());
        values().onChange(event -> {
            Predicate<T> p = disabledPredicate.get();
            if (p != null) {
                switch (event.eventType()) {
                    case ADD: {
                        if (p.test(event.newValue())) {
                            disabledSelection.add(event.newValue());
                        }
                        break;
                    }
                    case UPDATE: {
                        if (p.test(event.newValue())) {
                            disabledSelection.add(event.newValue());
                        }
                        break;
                    }
                }
            }
        });
        multipleValues.set(acceptMulti);
        propagateEvents(values, selection, disabledSelection, multipleValues);

        Applications.LastIndexTracker lastIndexTracker = new Applications.LastIndexTracker();
        selection().indices().onChange(lastIndexTracker);
        selection().adjusters().add(new PropertyAdjuster() {
            @Override
            public void adjust(PropertyAdjusterContext context) {
                T v = (T) context.newValue();
                if (isDisabledItem(v)) {
                    WritableList<T> values = ChoiceBase.this.values();
                    int newIndex = Applications.bestSelectableIndex(values,
                            ChoiceBase.this::isDisabledItem,
                            values.findFirstIndexOf(v), lastIndexTracker.getLastIndex());
                    if (newIndex >= 0) {
                        context.doInstead(() -> {
                            ChoiceBase.this.selection().add(
                                    ChoiceBase.this.values().get(newIndex)
                            );
                        });
                    } else {
                        context.doNothing();
                    }
                }
            }
        });
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

    private void revalidateDisabled() {
        Predicate<T> v = disabledPredicate.get();
        if (v != null) {
            List<T> d = values().stream().filter(x -> v.test(x)).collect(Collectors.toList());
            disabledSelection.clear();
            for (T t : d) {
                disabledSelection.add(t);
            }
        }
    }

    public WritableValue<Predicate<T>> disabledPredicate() {
        return disabledPredicate;
    }

    private boolean isDisabledItem(T o) {
        return disabledSelection.contains(o);
    }

    public WritableListIndexSelection<T> disabledSelection() {
        return disabledSelection;
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
    public WritableBoolean multipleValues() {
        return multipleValues;
    }

    @Override
    public PropertyType itemType() {
        return itemType;
    }
}

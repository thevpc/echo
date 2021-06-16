package net.thevpc.echo.api.components;

import net.thevpc.common.props.*;

import java.util.function.Predicate;

public interface AppChoiceControl<T> extends AppControl {

    WritableValue<AppChoiceItemRenderer<T>> itemRenderer();

    PropertyType itemType();

    WritableList<T> values();

    WritableListIndexSelectionExt<T> selection();

}

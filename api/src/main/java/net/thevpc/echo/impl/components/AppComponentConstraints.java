package net.thevpc.echo.impl.components;

import net.thevpc.common.props.WritableList;
import net.thevpc.echo.constraints.AppComponentConstraint;

import java.util.List;

public interface AppComponentConstraints extends WritableList<AppComponentConstraint> {
    void remove(Class<? extends AppComponentConstraint> any);

    <T extends AppComponentConstraint> List<T> getAll(Class<T> c);

    <T extends AppComponentConstraint> T get(Class<T> c);
}

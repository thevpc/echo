package net.thevpc.echo.api;

import net.thevpc.common.props.WritableList;
import net.thevpc.echo.constraints.AppComponentConstraint;
import net.thevpc.echo.constraints.AppParentConstraint;

import java.util.List;

public interface AppParentConstraints extends WritableList<AppParentConstraint> {
    void remove(Class<? extends AppParentConstraint> any);

    <T extends AppParentConstraint> List<T> getAll(Class<T> c);

    <T extends AppParentConstraint> T get(Class<T> c);
}

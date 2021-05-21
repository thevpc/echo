package net.thevpc.echo.api;

import net.thevpc.common.props.WritableList;
import net.thevpc.echo.constraints.AppChildConstraint;
import net.thevpc.echo.constraints.AppParentConstraint;

import java.util.List;

public interface AppChildConstraints extends WritableList<AppChildConstraint> {
    void remove(Class<? extends AppChildConstraint> any);

    <T extends AppChildConstraint> List<T> getAll(Class<T> c);

    <T extends AppChildConstraint> T get(Class<T> c);
}

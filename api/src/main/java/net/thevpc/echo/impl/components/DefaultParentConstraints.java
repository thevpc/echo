package net.thevpc.echo.impl.components;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.api.AppComponentConstraints;
import net.thevpc.echo.api.AppParentConstraints;
import net.thevpc.echo.constraints.AppComponentConstraint;
import net.thevpc.echo.constraints.AppParentConstraint;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultParentConstraints extends WritableListImpl<AppParentConstraint> implements AppParentConstraints {
    public DefaultParentConstraints(String name) {
        super(name, PropertyType.of(AppParentConstraint.class));
    }

    @Override
    public void remove(Class<? extends AppParentConstraint> any) {
        for (AppParentConstraint constraint : findAll(x -> any.isInstance(x))) {
            remove(constraint);
        }
    }

    @Override
    protected boolean addImpl(int index, AppParentConstraint v) {
        if(v!=null) {
            remove(v.getClass());
            return super.addImpl(index, v);
        }
        return false;
    }

    @Override
    public <T extends AppParentConstraint> List<T> getAll(Class<T> c) {
        return (List<T>) toList().stream().filter(x->c.isInstance(x)).collect(Collectors.toList());
    }

    @Override
    public <T extends AppParentConstraint> T get(Class<T> c) {
        return (T) toList().stream().filter(x->c.isInstance(x))
                .findFirst().orElse(null);
    }

}

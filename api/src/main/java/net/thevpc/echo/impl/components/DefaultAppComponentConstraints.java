package net.thevpc.echo.impl.components;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.constraints.AppComponentConstraint;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultAppComponentConstraints extends WritableListImpl<AppComponentConstraint> implements AppComponentConstraints {
    public DefaultAppComponentConstraints(String name) {
        super(name, PropertyType.of(AppComponentConstraint.class));
    }

    @Override
    public void remove(Class<? extends AppComponentConstraint> any) {
        for (AppComponentConstraint constraint : findAll(x -> any.isInstance(x))) {
            remove(constraint);
        }
    }

    @Override
    protected boolean addImpl(int index, AppComponentConstraint v) {
        if(v!=null) {
            remove(v.getClass());
            return super.addImpl(index, v);
        }
        return false;
    }

    @Override
    public <T extends AppComponentConstraint> List<T> getAll(Class<T> c) {
        return (List<T>) toList().stream().filter(x->c.isInstance(x)).collect(Collectors.toList());
    }

    @Override
    public <T extends AppComponentConstraint> T get(Class<T> c) {
        return (T) toList().stream().filter(x->c.isInstance(x))
                .findFirst().orElse(null);
    }

}

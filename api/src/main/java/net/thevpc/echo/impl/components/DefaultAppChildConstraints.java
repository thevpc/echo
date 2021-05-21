package net.thevpc.echo.impl.components;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.api.AppChildConstraints;
import net.thevpc.echo.api.AppComponentConstraints;
import net.thevpc.echo.constraints.AppChildConstraint;
import net.thevpc.echo.constraints.AppComponentConstraint;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultAppChildConstraints extends WritableListImpl<AppChildConstraint> implements AppChildConstraints {
    public DefaultAppChildConstraints(String name) {
        super(name, PropertyType.of(AppChildConstraint.class));
    }

    @Override
    public void remove(Class<? extends AppChildConstraint> any) {
        for (AppChildConstraint constraint : findAll(x -> any.isInstance(x))) {
            remove(constraint);
        }
    }

    @Override
    protected boolean addImpl(int index, AppChildConstraint v) {
        if(v!=null) {
            remove(v.getClass());
            return super.addImpl(index, v);
        }
        return false;
    }

    @Override
    public <T extends AppChildConstraint> List<T> getAll(Class<T> c) {
        return (List<T>) toList().stream().filter(x->c.isInstance(x)).collect(Collectors.toList());
    }

    @Override
    public <T extends AppChildConstraint> T get(Class<T> c) {
        return (T) toList().stream().filter(x->c.isInstance(x))
                .findFirst().orElse(null);
    }

}

package net.thevpc.echo.impl.components;

import net.thevpc.common.props.PropertyAdjuster;
import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.PropertyUpdate;
import net.thevpc.common.props.impl.PropertyAdjusterContext;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.api.AppChildConstraints;
import net.thevpc.echo.api.AppComponentConstraints;
import net.thevpc.echo.constraints.AppChildConstraint;
import net.thevpc.echo.constraints.AppComponentConstraint;
import net.thevpc.echo.constraints.AppParentConstraint;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultAppChildConstraints extends WritableListImpl<AppChildConstraint> implements AppChildConstraints {
    public DefaultAppChildConstraints(String name) {
        super(name, PropertyType.of(AppChildConstraint.class));

        adjusters().add(new PropertyAdjuster() {
            @Override
            public void adjust(PropertyAdjusterContext context) {
                if(context.event().eventType()== PropertyUpdate.ADD) {
                    AppChildConstraint n = (AppChildConstraint) context.newValue();
                    if (n == null) {
                        context.doNothing();
                        return;
                    }
                    List<AppChildConstraint> old = findAll(x -> n.getClass().isInstance(x));
                    if (old.size() > 0) {
                        context.doInstead(() -> {
                            for (AppChildConstraint o : old) {
                                remove(o);
                            }
                            add(n);
                        });
                        return;
                    }
                }
            }
        });

    }

    @Override
    public void remove(Class<? extends AppChildConstraint> any) {
        for (AppChildConstraint constraint : findAll(x -> any.isInstance(x))) {
            remove(constraint);
        }
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

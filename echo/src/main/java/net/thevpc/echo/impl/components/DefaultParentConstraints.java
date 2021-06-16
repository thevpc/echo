package net.thevpc.echo.impl.components;

import net.thevpc.common.props.PropertyAdjuster;
import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.PropertyUpdate;
import net.thevpc.common.props.impl.PropertyAdjusterContext;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.api.AppComponentConstraints;
import net.thevpc.echo.api.AppParentConstraints;
import net.thevpc.echo.constraints.AppComponentConstraint;
import net.thevpc.echo.constraints.AppParentConstraint;
import net.thevpc.echo.constraints.Layout;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultParentConstraints extends WritableListImpl<AppParentConstraint> implements AppParentConstraints {
    public DefaultParentConstraints(String name) {
        super(name, PropertyType.of(AppParentConstraint.class));
        adjusters().add(new PropertyAdjuster() {
            @Override
            public void adjust(PropertyAdjusterContext context) {
                if(context.event().eventType()== PropertyUpdate.ADD) {
                    AppParentConstraint n = (AppParentConstraint) context.newValue();
                    if (n == null) {
                        context.doNothing();
                        return;
                    }
                    List<AppParentConstraint> old = findAll(x -> n.getClass().isInstance(x));
                    if (old.size() > 0) {
                        if(old.size()==1 && old.get(0).equals(n)){
                            context.doNothing();
                        }else {
                            context.doInstead(() -> {
                                for (AppParentConstraint o : old) {
                                    remove(o);
                                }
                                add(n);
                            });
                        }
                        return;
                    }
                }
            }
        });
    }

    @Override
    public void remove(Class<? extends AppParentConstraint> any) {
        for (AppParentConstraint constraint : findAll(x -> any.isInstance(x))) {
            remove(constraint);
        }
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

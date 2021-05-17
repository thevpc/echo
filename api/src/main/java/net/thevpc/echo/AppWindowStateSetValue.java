package net.thevpc.echo;

import java.util.HashSet;
import java.util.Set;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.impl.WritablePropertyDelegate;
import net.thevpc.common.props.WritableValue;
import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.impl.WritableValueDelegate;

public class AppWindowStateSetValue extends WritableValueDelegate<AppWindowStateSet> {

    public AppWindowStateSetValue(String name) {
        super(Props.of(name).valueOf(AppWindowStateSet.class, new AppWindowStateSet()));
    }

    @Override
    protected WritableValue<AppWindowStateSet> getBase() {
        return (WritableValue<AppWindowStateSet>) super.getBase();
    }

    public void add(AppWindowState a) {
        getBase().set(getBase().get().add(a));
    }

    public void remove(AppWindowState a) {
        getBase().set(getBase().get().remove(a));
    }

    public boolean is(AppWindowState a) {
        return getBase().get().is(a);
    }

    public Set<AppWindowState> values() {
        return new HashSet<>(getBase().get().values());
    }

    @Override
    public String toString() {
        return getBase().toString();
    }

    @Override
    public ObservableValue<AppWindowStateSet> readOnly() {
        return getBase().readOnly();
    }

}

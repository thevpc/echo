package net.thevpc.echo;

import java.util.HashSet;
import java.util.Set;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.impl.WritableValueDelegate;

public class WindowStateSetValue extends WritableValueDelegate<WindowStateSet> {

    public WindowStateSetValue(String name) {
        super(Props.of(name).valueOf(WindowStateSet.class, new WindowStateSet()));
    }

    @Override
    protected WritableValue<WindowStateSet> getBase() {
        return (WritableValue<WindowStateSet>) super.getBase();
    }

    public void add(WindowState a) {
        getBase().set(getBase().get().add(a));
    }

    public void remove(WindowState a) {
        getBase().set(getBase().get().remove(a));
    }

    public boolean is(WindowState a) {
        return getBase().get().is(a);
    }

    public Set<WindowState> values() {
        return new HashSet<>(getBase().get().values());
    }

    @Override
    public String toString() {
        return getBase().toString();
    }

    @Override
    public ObservableValue<WindowStateSet> readOnly() {
        return getBase().readOnly();
    }

}

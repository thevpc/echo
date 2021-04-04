package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.impl.DelegateProperty;
import net.thevpc.common.props.WritableValue;
import net.thevpc.common.props.ObservableValue;

public class AppWindowStateSetValue extends DelegateProperty<AppWindowStateSet> {

    public AppWindowStateSetValue(String name) {
        super(Props.of(name).valueOf(AppWindowStateSet.class, new AppWindowStateSet()));
    }

    @Override
    public WritableValue<AppWindowStateSet> getBase() {
        return (WritableValue<AppWindowStateSet>) super.getBase();
    }

    public void add(AppWindowState a) {
        getBase().set(getBase().get().add(a));
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

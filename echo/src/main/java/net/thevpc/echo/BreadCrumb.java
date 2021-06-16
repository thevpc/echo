package net.thevpc.echo;

import net.thevpc.common.props.PropertyType;
import net.thevpc.echo.api.components.AppBreadCrumb;
import net.thevpc.echo.impl.components.ChoiceBase;
import net.thevpc.echo.spi.peers.AppBreadCrumbPeer;

public class BreadCrumb<T> extends ChoiceBase<T> implements AppBreadCrumb<T> {

    public BreadCrumb(String id, PropertyType itemType, Application app) {
        super(id, itemType, false, app, AppBreadCrumb.class, AppBreadCrumbPeer.class);
    }

    public BreadCrumb(String id, Class<T> valueType, Application app) {
        this(id, PropertyType.of(valueType), app);
    }

    public BreadCrumb(Class<T> valueType, Application app) {
        this(null, valueType, app);
    }

}

package net.thevpc.echo;

import net.thevpc.common.props.PropertyContainer;

public interface AppToolContainer extends PropertyContainer, AppComponent {
    AppNode rootNode();

    AppTools tools();

}

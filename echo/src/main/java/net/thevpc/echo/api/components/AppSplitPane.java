package net.thevpc.echo.api.components;


import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Orientation;

public interface AppSplitPane extends AppContainer<AppComponent> {
    WritableValue<Orientation> orientation();
}

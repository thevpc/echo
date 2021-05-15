package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableInt;
import net.thevpc.common.props.WritableMap;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.WritableStr;
import net.thevpc.echo.props.WritableImage;


public interface AppTool {

    String id();

    WritableImage smallIcon();

    WritableImage largeIcon();

    WritableInt mnemonic();
    
    WritableString accelerator();
    
    WritableBoolean enabled();

    WritableBoolean visible();

    WritableStr title();
    
    WritableStr tooltip();

    WritableMap<Object,Object> properties();

    Application app();
}

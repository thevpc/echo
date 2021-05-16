package net.thevpc.echo.api.tools;

import net.thevpc.common.props.*;
import net.thevpc.echo.Application;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.iconset.WritableImage;


public interface AppTool extends Property {

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

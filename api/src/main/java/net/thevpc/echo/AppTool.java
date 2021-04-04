package net.thevpc.echo;

import net.thevpc.echo.props.AppWritableIcon;
import net.thevpc.echo.props.AppWritableString;
import net.thevpc.common.props.WritableValue;


public interface AppTool {

    String id();

    AppWritableIcon smallIcon();

    AppWritableIcon largeIcon();

    WritableValue<Integer> mnemonic();
    
    WritableValue<String> accelerator();
    
    WritableValue<Boolean> enabled();

    WritableValue<Boolean> visible();

    AppWritableString title();
    
    AppWritableString tooltip();
    
    AppTools tools();

    <T extends AppTool> AppToolComponent<T> copyTo(AppTools tools, String newPath);
}

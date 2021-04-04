package net.thevpc.echo;


import javax.swing.*;
import java.io.File;
import net.thevpc.common.props.WritableValue;
import net.thevpc.common.props.ObservableValue;

public interface AppIconSet {
    WritableValue<String> id();

    ObservableValue<ImageIcon> icon(String id);

    WritableValue<AppIconResolver> resolver();

    String iconIdForFile(File f, boolean selected, boolean expanded);

    String iconIdForFileName(String f, boolean selected, boolean expanded);

    ObservableValue<ImageIcon> iconForFile(File f, boolean selected, boolean expanded);

    ObservableValue<ImageIcon> iconForFileName(String f, boolean selected, boolean expanded);
}

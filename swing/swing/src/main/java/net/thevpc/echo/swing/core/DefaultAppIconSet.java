package net.thevpc.echo.swing.core;

import net.thevpc.echo.AppIconResolver;
import net.thevpc.echo.AppIconSet;
import net.thevpc.common.iconset.IconSet;
import net.thevpc.common.props.*;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DefaultAppIconSet implements AppIconSet {
    protected WritableValue<String> id = Props.of("id").valueOf(String.class, null);
    protected WritableValue<AppIconResolver> resolver = Props.of("id").valueOf(AppIconResolver.class, null);
    private Map<String, WritableValue<ImageIcon>> icons = new HashMap<>();
    private ObservableMap<String, IconSet> iconSets;

    public DefaultAppIconSet(ObservableMap<String, IconSet> iconSets0) {
        this.iconSets = iconSets0;
        id.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                IconSet s = iconSets.get((String) event.getNewValue());
                for (Map.Entry<String, WritableValue<ImageIcon>> entry : icons.entrySet()) {
                    entry.getValue().set(s == null ? null : s.getIcon(entry.getKey()));
                }
            }
        });
    }

    @Override
    public WritableValue<String> id() {
        return id;
    }

    @Override
    public ObservableValue<ImageIcon> icon(String id) {
        WritableValue<ImageIcon> i = icons.get(id);
        if (i == null) {
            IconSet s = iconSets.get(this.id.get());
            i = Props.of("icon-" + id).valueOf(ImageIcon.class, s == null ? null : s.getIcon(id));
            icons.put(id, i);
        }
        return i;
    }

    @Override
    public WritableValue<AppIconResolver> resolver() {
        return resolver;
    }

    @Override
    public String iconIdForFile(File f, boolean selected, boolean expanded) {
        AppIconResolver r = resolver.get();
        if(r ==null){
            return null;
        }
        return r.iconIdForFile(f,selected,expanded);
    }

    @Override
    public String iconIdForFileName(String f, boolean selected, boolean expanded) {
        AppIconResolver r = resolver.get();
        if(r ==null){
            return null;
        }
        return r.iconIdForFileName(f,selected,expanded);
    }

    @Override
    public ObservableValue<ImageIcon> iconForFile(File f, boolean selected, boolean expanded) {
        return icon(iconIdForFile(f,selected,expanded));
    }

    @Override
    public ObservableValue<ImageIcon> iconForFileName(String f, boolean selected, boolean expanded) {
        return icon(iconIdForFileName(f,selected,expanded));
    }
}

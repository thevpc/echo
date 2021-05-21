package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComboBox;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.components.ChoiceBase;
import net.thevpc.echo.spi.peers.AppComboBoxPeer;

public class ComboBox<T> extends ChoiceBase<T> implements AppComboBox<T> {
    public ComboBox(Class<T> itemType, Application app) {
        this(null, itemType, app);
    }

    public ComboBox(String id, Class<T> itemType, Application app) {
        super(id, itemType, false, app,
                (Class<? extends AppComponent>) AppComboBox.class, AppComboBoxPeer.class);
    }



}


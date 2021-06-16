package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFontButton;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.components.FontBase;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class FontButton extends FontBase implements AppFontButton, AppContentAdapter {
    private Button button;
    private FontChooser fc;
    private AppComponent owner;

    public FontButton(AppComponent owner,Application app) {
        this(null, owner,app);
    }

    public FontButton(String id, AppComponent owner,Application app) {
        super(id, app, AppFontButton.class, AppComponentPeer.class);
        this.owner=owner;
        button = new Button(id(), app());
        Applications.bindContent(this);
        button.icon().set(Str.of("font-type"));
        selection().onChangeAndInit(() -> {
            AppFont f = selection().get();
            button.text().set(
                    f == null ? Str.i18n("FontChooser.defaultFont") :
                            Str.of(f.family())
            );
        });
        fc = new FontChooser(app());
        button.action().set(() -> {
            fc.selection().set(selection().get());
            if (fc.showDialog(owner==null?FontButton.this:owner)) {
                selection().set(fc.selection().get());
            }
        });

    }

    @Override
    public AppComponentPeer peer(boolean prepareShowing) {
//        if(prepareShowing && peer==null){
//            if(button==null){
//                button = new Button(id(), app());
//                Applications.bindContent(this);
//                button.icon().set(Str.of("font-type"));
//                selection().onChangeAndInit(() -> {
//                    AppFont f = selection().get();
//                    button.text().set(
//                            f == null ? Str.i18n("FontChooser.defaultFont") :
//                                    Str.of(f.family())
//                    );
//                });
//                fc = new FontChooser(app());
//                button.action().set(() -> {
//                    fc.selection().set(selection().get());
//                    if (fc.showDialog(FontButton.this)) {
//                        selection().set(fc.selection().get());
//                    }
//                });
//            }
//        }
        return super.peer(prepareShowing);
    }

    @Override
    public AppComponent content() {
        return button;
    }
}


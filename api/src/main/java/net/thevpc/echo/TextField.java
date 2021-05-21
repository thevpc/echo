package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.impl.components.TextBase;
import net.thevpc.echo.spi.peers.AppTextFieldPeer;

public class TextField extends TextBase implements AppTextField {
    public TextField(Str str, Application app) {
        this(null,str,app);
    }
    public TextField(String id,Str str, Application app) {
        super(id,str,app,AppTextField.class, AppTextFieldPeer.class);
    }
    public TextField(Application app) {
        this(null,null,app);
    }

}
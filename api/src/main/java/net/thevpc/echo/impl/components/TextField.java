package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.api.peers.AppTemporalFieldPeer;
import net.thevpc.echo.api.peers.AppTextFieldPeer;
import net.thevpc.echo.api.tools.AppTextModel;
import net.thevpc.echo.impl.tools.TextModel;

public class TextField extends TextBase implements AppTextField {
    public TextField(Str str, Application app) {
        this(new TextModel(str,app));
    }
    public TextField(AppTextModel tool) {
        super(tool,
                AppTextModel.class,AppTextField.class, AppTextFieldPeer.class
                );
    }
    public TextField(Application app) {
        this(new TextModel(app));
    }

}
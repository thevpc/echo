package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppPasswordField;
import net.thevpc.echo.api.peers.AppPasswordFieldPeer;
import net.thevpc.echo.api.tools.AppTextModel;
import net.thevpc.echo.impl.tools.TextModel;

public class PasswordField extends TextBase implements AppPasswordField {
    public PasswordField(AppTextModel tool) {
        super(tool,
                AppTextModel.class,AppPasswordField.class, AppPasswordFieldPeer.class
                );
    }

    public PasswordField(Application tool) {
        this(new TextModel(tool));
    }

    public AppTextModel model() {
        return (AppTextModel) super.model();
    }

}


package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppPasswordField;
import net.thevpc.echo.api.components.AppTextArea;
import net.thevpc.echo.api.peers.AppPasswordFieldPeer;
import net.thevpc.echo.api.tools.AppTextModel;
import net.thevpc.echo.impl.tools.TextModel;

public class TextArea extends TextBase implements AppTextArea {
    public TextArea(Str str, Application app) {
        this(new TextModel(str,app));
    }
    public TextArea(AppTextModel tool) {
        super(tool,
                AppTextModel.class, AppPasswordField.class, AppPasswordFieldPeer.class
        );
    }

    public TextArea(Application app) {
        this(new TextModel(app));
    }

    public AppTextModel model() {
        return (AppTextModel) super.model();
    }
}

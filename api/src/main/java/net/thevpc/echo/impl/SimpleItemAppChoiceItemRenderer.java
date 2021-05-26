package net.thevpc.echo.impl;

import net.thevpc.echo.Application;
import net.thevpc.echo.SimpleItem;
import net.thevpc.echo.api.components.AppChoiceItemContext;
import net.thevpc.echo.api.components.AppChoiceItemRenderer;

public class SimpleItemAppChoiceItemRenderer implements AppChoiceItemRenderer<SimpleItem> {

    private final Application app;

    public SimpleItemAppChoiceItemRenderer(Application app) {
        this.app = app;
    }

    @Override
    public void render(AppChoiceItemContext<SimpleItem> context) {
        SimpleItem v = context.getValue();
        if (v != null) {
            String icon = v.getIcon();
            context.renderDefault();
            context.setText(v.getName().value(context.getApplication().i18n(),context.getChoice().locale().get()));
            context.setIcon(
                    (icon != null && icon.length() > 0) ? app.iconSets().icon(icon).get() : null
            );
        } else {
            context.renderDefault();
        }
    }
}

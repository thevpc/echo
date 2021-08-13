package net.thevpc.echo.api.components;

import net.thevpc.echo.api.AppAlertResult;

public interface AppColorChooser extends AppColorControl {
    default AppAlertResult showDialog() {
        return showDialog(null);
    }

    /**
     * @param owner owner
     * @return return button ids: 'selectColor', 'selectNoColor', 'cancel'
     */
    AppAlertResult showDialog(AppComponent owner);
}

package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppAlertModel;

public class AlertModel extends AppComponentModelBase implements AppAlertModel {

    public AlertModel(String id, Application app) {
        super(id, app);
    }

    public AlertModel(Application app) {
        super(null, app);
    }


}

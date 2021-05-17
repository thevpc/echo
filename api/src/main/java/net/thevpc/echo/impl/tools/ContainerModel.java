package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppContainerModel;

public class ContainerModel extends AppComponentModelBase implements AppContainerModel {

    public ContainerModel(String id, Application app) {
        super(id,app);
    }
    public ContainerModel(Application app) {
        super(null,app);
    }
}

//package net.thevpc.echo.model;
//
//import net.thevpc.common.props.Props;
//import net.thevpc.common.props.WritableValue;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.api.model.AppTemporalModel;
//
//import java.time.temporal.Temporal;
//
//public class TemporalModel<T extends Temporal> extends AppComponentModelBase implements AppTemporalModel<T> {
//
//    public TemporalModel(String id, Class<T> type, Application app) {
//        super(id, app);
//    }
//
//    public TemporalModel(Class<T> type, Application app) {
//        this(null, type, app);
//    }
//
//}

//package net.thevpc.echo.impl;
//
//import net.thevpc.common.props.PropertyEvent;
//import net.thevpc.common.props.PropertyListener;
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.model.AppComponentModel;
//
//import java.util.*;
//
//public class GlobalAppTools extends AbstractAppToolsBase {
//
//    private List<AddToolArgs> tools0 = new ArrayList<>();
//
//    public GlobalAppTools(DefaultApplication application) {
//        super(application);
//        this.application.state().onChange(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                AppState s = (AppState) event.getNewValue();
//                if (s == AppState.INIT) {
//                    for (Iterator<AddToolArgs> iterator = tools0.iterator(); iterator.hasNext();) {
//                        AddToolArgs tool = iterator.next();
//                        iterator.remove();
//                        addTool(tool.tool,tool.path,tool.options);
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public AppComponent getComponent(Path path0) {
//        return application.root().get(path0);
//    }
//
//    private static class AddToolArgs{
//        AppComponentModel tool; Path path; AppComponentOptions options;
//
//        public AddToolArgs(AppComponentModel tool, Path path, AppComponentOptions options) {
//            this.tool = tool;
//            this.path = path;
//            this.options = options;
//        }
//    }
//    @Override
//    public AppComponent addTool(AppComponentModel tool, Path path, AppComponentOptions options) {
//        if (this.application.state().get() == AppState.NONE) {
//            this.tools0.add(new AddToolArgs(tool,path, options));
//            return null;
//        }
//        return application.root().add(tool,path,options);
//    }
//
//    @Override
//    public void removeTool(Path tool) {
//        application.root().remove(tool);
//    }
//
////    protected void addRootContainer(AppContainer c) {
////        if (c != null) {
////            c.components().onChange(toolMapResolverAppPropertyListener);
////        }
////    }
////
////    protected void removeRootContainer(AppContainer c) {
////        if (c != null) {
////            c.components().onChange(toolMapResolverAppPropertyListener);
////        }
////    }
//}

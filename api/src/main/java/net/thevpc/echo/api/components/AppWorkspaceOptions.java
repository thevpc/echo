//package net.thevpc.echo.api.components;
//
//import net.thevpc.echo.DefaultAppComponentOptions;
//
//public class AppWorkspaceOptions extends DefaultAppComponentOptions implements Cloneable {
//
//    private Boolean enabledDesktop;
//    private Boolean enabledDocking;
//
//    public Boolean enabledDesktop() {
//        return enabledDesktop;
//    }
//
//    public AppWorkspaceOptions enabledDesktop(Boolean enabledDesktop) {
//        this.enabledDesktop = enabledDesktop;
//        return this;
//    }
//
//    public boolean enabledDocking() {
//        return enabledDocking;
//    }
//
//    public AppWorkspaceOptions enabledDocking(Boolean enabledDocking) {
//        this.enabledDocking = enabledDocking;
//        return this;
//    }
//
//    @Override
//    public void copyFrom(AppComponentOptions other, boolean nonNull) {
//        super.copyFrom(other, nonNull);
//        if (other instanceof AppWorkspaceOptions) {
//            AppWorkspaceOptions o = (AppWorkspaceOptions) other;
//            if (!nonNull || o.enabledDesktop != null) {
//                this.enabledDesktop = o.enabledDesktop;
//            }
//            if (!nonNull || o.enabledDocking != null) {
//                this.enabledDocking = o.enabledDocking;
//            }
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "AppWorkspaceOptions{"
//                + "order=" + order()
//                + "enabledDesktop=" + enabledDesktop()
//                + ", enabledDocking=" + enabledDocking()
//                + '}';
//    }
//}

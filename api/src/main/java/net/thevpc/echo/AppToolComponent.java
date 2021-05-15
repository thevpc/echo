//package net.thevpc.echo;
//
//
//public interface AppToolComponent<T extends AppTool> extends AppControl {
//
//    static <T extends AppTool> AppToolComponent<T> of(T tool, String path) {
//        return new DefaultAppToolComponent<T>(tool, path, 0, null);
//    }
//
//    static <T extends AppTool> AppToolComponent<T> of(T tool, String path, int order) {
//        return new DefaultAppToolComponent<T>(tool, path, order, null);
//    }
//
//    static <T extends AppTool> AppToolComponent<T> of(T tool, String path, int order, AppControlRenderer renderer) {
//        return new DefaultAppToolComponent<T>(tool, path, order, renderer);
//    }
//
//    AppToolComponent<T> copyTo(AppTools tools, String newPath);
//
//    T tool();
//
//    int order();
//}

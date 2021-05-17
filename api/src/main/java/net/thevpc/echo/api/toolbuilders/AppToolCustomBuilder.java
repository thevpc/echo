///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.api.toolbuilders;
//
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppComponentType;
//import net.thevpc.echo.api.tools.AppComponentModel;
//
//import java.util.List;
//import java.util.function.Supplier;
//
///**
// * @author vpc
// */
//public interface AppToolCustomBuilder extends AppToolBuilder{
//
//    AppToolCustomBuilder renderer(Supplier<?> m);
//
//    @Override
//    AppToolCustomBuilder id(String id);
//
//    @Override
//    AppToolCustomBuilder path(String... path);
//
//    @Override
//    AppComponentModel tool();
//
//    @Override
//    AppComponent component();
//
//    @Override
//    List<AppComponent> components();
//
//    AppToolCustomBuilder options(AppComponentOptions options);
//
//    AppToolCustomBuilder order(Integer id);
//
//    AppToolCustomBuilder componentType(AppComponentType componentType);
//}

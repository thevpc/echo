///**
// * ====================================================================
// * Echo : Simple Desktop Application Framework
// * <br>
// * Echo is a simple Desktop Application Framework witj productivity in mind.
// * Currently Echo has two ports : swing and javafx
// * <br>
// *
// * Copyright [2021] [thevpc] Licensed under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
// * or agreed to in writing, software distributed under the License is
// * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied. See the License for the specific language
// * governing permissions and limitations under the License.
// * <br> ====================================================================
// */
//
//
//
//package net.thevpc.echo.api.toolbuilders;
//
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.components.AppAction;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppComponentType;
//import net.thevpc.echo.api.tools.AppToolAction;
//
//import java.util.List;
//
///**
// *
// * @author vpc
// */
//public interface AppToolActionBuilder extends AppToolBuilder {
//
//    @Override
//    AppToolActionBuilder id(String id);
//
//    @Override
//    AppToolActionBuilder path(String... path);
//
//    @Override
//    AppToolAction tool();
//
//    @Override
//    AppComponent component();
//
//    @Override
//    List<AppComponent> components();
//
//    AppToolActionBuilder bindUndo(AppUndoableActionSupplier a);
//
//    AppToolActionBuilder bind(AppAction a);
//
//    AppToolActionBuilder bind(Runnable a);
//
//
//    AppToolActionBuilder options(AppComponentOptions options);
//
//    AppToolActionBuilder order(Integer id);
//
//    AppToolActionBuilder componentType(AppComponentType componentType);
//}

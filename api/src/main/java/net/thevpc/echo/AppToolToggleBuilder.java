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
//package net.thevpc.echo;
//
//import java.util.List;
//
//import net.thevpc.common.props.WritableBoolean;
//import net.thevpc.common.props.WritableValue;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppComponentType;
//import net.thevpc.echo.api.components.AppToggle;
//import net.thevpc.echo.api.toolbuilders.AppToolBuilder;
//import net.thevpc.echo.api.tools.AppToggleModel;
//
///**
// *
// * @author vpc
// */
//public interface AppToolToggleBuilder extends AppToolBuilder {
//
//    AppToolToggleBuilder bind(AppToolToggleModel m);
//
//    AppToolToggleBuilder bind(WritableBoolean s);
//
//    <T> AppToolToggleBuilder bind(WritableValue<T> s, T value);
//
//    @Override
//    AppToolToggleBuilder id(String id);
//
//    AppToolToggleBuilder group(String group);
//
//    @Override
//    AppToolToggleBuilder path(String... path);
//
//    @Override
//    public AppToggleModel tool();
//
//    @Override
//    public AppToggle component();
//
//    @Override
//    public List<AppToggle> components();
//
//    AppToolToggleBuilder options(AppComponentOptions options);
//
//    AppToolToggleBuilder order(Integer id);
//
//    AppToolToggleBuilder componentType(AppComponentType componentType);
//}

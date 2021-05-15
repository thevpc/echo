///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.impl.toolbuilders;
//
//import net.thevpc.common.props.PropertyEvent;
//import net.thevpc.common.props.PropertyListener;
//import net.thevpc.common.props.WritableValue;
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppToggle;
//import net.thevpc.echo.api.tools.AppToolToggle;
//import net.thevpc.echo.impl.AbstractAppToolsBase;
//
//import java.util.Objects;
//
///**
// *
// * @author vpc
// */
//public class DefaultAppToolToggleBuilder
//        extends AbstractAppToolBuilder<
//                AppToolToggle, AppToggle,
//        AppComponentOptions,AppToolToggleBuilder>
//        implements AppToolToggleBuilder {
//
//    private String group;
//    private AppToolToggleModel property;
//
//    public DefaultAppToolToggleBuilder(AbstractAppToolsBase tools) {
//        super(tools);
//    }
//
//    public AppToolToggleBuilder group(String g) {
//        this.group = g;
//        return this;
//    }
//
//    @Override
//    public AppToolToggleBuilder bind(WritableValue<Boolean> s) {
//        this.property = new AppToolToggleModel() {
//            @Override
//            public boolean isSelected() {
//                return s.get();
//            }
//
//            @Override
//            public void setSelected(boolean b) {
//                s.set(b);
//            }
//        };
//        return this;
//    }
//
//    public <T> AppToolToggleBuilder bind(WritableValue<T> s, T value) {
//        this.property = new AppToolToggleModel() {
//            @Override
//            public boolean isSelected() {
//                return Objects.equals(s.get(), value);
//            }
//
//            @Override
//            public void setSelected(boolean b) {
//                if (b) {
//                    s.set(value);
//                }
//            }
//        };
//        return this;
//    }
//
//    public AppToolToggleBuilder bind(AppToolToggleModel s) {
//        this.property = s;
//        return this;
//    }
//
//    public AppToggle buildFirst(AppComponentOptions options) {
//        ItemPath ipath = paths.get(0);
//        String _id = id;
//        if (_id == null) {
//            _id = ipath.isEmpty() ? "Action.Unknown" : "Action." + ipath.name();
//        }
//        AppToolToggle tool = new AppToolToggleImpl(_id, group, tools.app(), tools);
//        tool.title().setId(_id);
//        tool.smallIcon().setId("$" + _id + ".icon"); //the dollar meens the the icon key is resolved from i18n
//        AppToggle c = (AppToggle) tools.addTool(tool, ipath, options);
//        tool.group().set(group);
//        if(property!=null) {
//            boolean selected = property.isSelected();
//            tool.selected().set(selected);
//        }
//        //property.
//        tool.selected().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                boolean selected = event.getNewValue();
//                if(property!=null) {
//                    property.setSelected(selected);
//                }
//            }
//        });
//        return c;
//    }
//
//}

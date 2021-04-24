/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author vpc
 */
public interface AppToolCheckBoxBuilder<T> {

    AppToolCheckBoxBuilder<T> bind(Supplier<T> s, Consumer<T> c);

    AppToolCheckBoxBuilder<T> bind(WritableValue<T> s);

    AppToolCheckBoxBuilder<T> id(String id);

    AppToolCheckBoxBuilder<T> group(String group);

    AppToolCheckBoxBuilder<T> value(T value);

    AppToolCheckBoxBuilder<T> path(String... path);

    public AppToolCheckBox tool();

    public AppToolComponent<AppToolCheckBox> component();

    public List<AppToolComponent<AppToolCheckBox>> components();

}

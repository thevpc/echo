/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import java.util.List;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author vpc
 */
public interface AppToolToggleBuilder {

    AppToolToggleBuilder buttonType(AppToolButtonType type);

    AppToolToggleBuilder bind(AppToolToggleModel m);

    AppToolToggleBuilder bind(WritableValue<Boolean> s);

    <T> AppToolToggleBuilder bind(WritableValue<T> s, T value);

    AppToolToggleBuilder id(String id);

    AppToolToggleBuilder group(String group);

    AppToolToggleBuilder path(String... path);

    public AppToolToggle tool();

    public AppToolComponent<AppToolToggle> component();

    public List<AppToolComponent<AppToolToggle>> components();

}

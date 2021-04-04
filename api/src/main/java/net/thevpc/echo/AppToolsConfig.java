/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author vpc
 */
public class AppToolsConfig {
    private final WritableValue<Boolean> configurableTitle=Props.of("configurableTitle").valueOf(Boolean.class, true);
    private final WritableValue<Boolean> configurableTooltip=Props.of("configurableTitle").valueOf(Boolean.class, true);
    private final WritableValue<Boolean> configurableSmallIcon=Props.of("configurableTitle").valueOf(Boolean.class, true);
    private final WritableValue<Boolean> configurableLargeIcon=Props.of("configurableTitle").valueOf(Boolean.class, true);

    public WritableValue<Boolean> configurableTitle() {
        return configurableTitle;
    }

    public WritableValue<Boolean> configurableTooltip() {
        return configurableTooltip;
    }

    public WritableValue<Boolean> configurableSmallIcon() {
        return configurableSmallIcon;
    }

    public WritableValue<Boolean> configurableLargeIcon() {
        return configurableLargeIcon;
    }
}

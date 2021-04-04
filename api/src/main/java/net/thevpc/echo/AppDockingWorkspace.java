/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import javax.swing.JComponent;
import net.thevpc.common.props.ObservableMap;

/**
 *
 * @author thevpc
 */
public interface AppDockingWorkspace extends AppWorkspace {

    AppContentWindow addContent(String id, JComponent component);
    
    AppToolWindow addTool(String id, JComponent component, AppToolWindowAnchor anchor);

    Application application();

    ObservableMap<String, AppToolWindow> toolWindows();

    ObservableMap<String, AppContentWindow> contentWindows();

    AppContentWindow getContent(String id);

    AppToolWindow getTool(String id);
}

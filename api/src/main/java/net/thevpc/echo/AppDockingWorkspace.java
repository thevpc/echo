/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import net.thevpc.common.props.PMap;

/**
 *
 * @author thevpc
 */
public interface AppDockingWorkspace extends AppWorkspace {

    AppContentWindow addContent(String id, String title, ImageIcon icon, JComponent component);

    AppToolWindow addTool(String id, String title, ImageIcon icon, JComponent component, AppToolWindowAnchor anchor);

    Application application();

    PMap<String, AppToolWindow> toolWindows();

    PMap<String, AppContentWindow> contentWindows();

    AppContentWindow getContent(String id);

    AppToolWindow getTool(String id);
}

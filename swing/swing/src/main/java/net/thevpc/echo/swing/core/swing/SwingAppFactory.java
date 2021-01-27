/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.AppMenuBar;
import net.thevpc.echo.AppPopupMenu;
import net.thevpc.echo.AppToolBar;
import net.thevpc.echo.Application;

/**
 *
 * @author thevpc
 */
public class SwingAppFactory {

    public AppMenuBar createMenuBar(Application app, String rootPath) {
        return new JAppMenuBar(rootPath, app);
    }

    public AppPopupMenu createPopupMenu(Application app, String rootPath) {
        return new JAppPopupMenu(rootPath, app);
    }

    public AppToolBar createToolBar(Application app, String rootPath) {
        return new JAppToolBarGroup(rootPath, app);
    }
}

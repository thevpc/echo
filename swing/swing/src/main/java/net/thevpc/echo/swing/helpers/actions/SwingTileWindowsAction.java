/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.helpers.actions;

import net.thevpc.echo.Application;
import net.thevpc.common.swing.win.InternalWindowsHelper;

import java.awt.event.ActionEvent;

/**
 *
 * @author thevpc
 */
public class SwingTileWindowsAction extends SwingAbstractAppAction {
    private InternalWindowsHelper wins;
    public SwingTileWindowsAction(Application aplctn, InternalWindowsHelper wins) {
        super(aplctn, "TileWindows");
        this.wins=wins;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        wins.tileFrames();
    }

}

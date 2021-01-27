/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.actions;

import java.awt.event.ActionEvent;

import net.thevpc.echo.AbstractAppAction;
import net.thevpc.echo.Application;
import net.thevpc.swings.plaf.UIPlafManager;

/**
 *
 * @author thevpc
 */
public class PlafAction extends AbstractAppAction {

    private String plaf;

    public PlafAction(Application aplctn, String plaf, String name, String icon,String desription) {
        super(aplctn, "Plaf_" + plaf,name, icon, desription);
        this.plaf = plaf;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        UIPlafManager.INSTANCE.apply(plaf);
    }
    @Override
    public void refresh() {

    }

}

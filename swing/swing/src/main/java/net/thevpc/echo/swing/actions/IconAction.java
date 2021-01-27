/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.actions;

import java.awt.event.ActionEvent;

import net.thevpc.echo.AbstractAppAction;
import net.thevpc.echo.Application;

/**
 *
 * @author thevpc
 */
public class IconAction extends AbstractAppAction {
    
    private String iconSet;

    public IconAction(Application aplctn, String iconSet) {
        super(aplctn, "IconSet_" + iconSet);
        this.iconSet = iconSet;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        getApplication().iconSet().id().set(iconSet);
    }
        @Override
    public void refresh() {

    }

}

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
public class IconSizeAction extends AbstractAppAction {

    private int size;

    public IconSizeAction(Application aplctn, int size) {
        super(aplctn, "IconSetSize_" + size);
        this.size = size;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        getApplication().iconSets()
                .config().set(getApplication().iconSets().config().get().setSize(size));
    }

    @Override
    public void refresh() {

    }

}

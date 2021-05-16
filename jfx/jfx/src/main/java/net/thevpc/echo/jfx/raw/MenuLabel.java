/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx.raw;

import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 *
 * @author vpc
 */
public class MenuLabel extends MenuButton {

    public MenuLabel() {
    }

    public MenuLabel(String string) {
        super(string);
    }

    public MenuLabel(String string, Node node) {
        super(string, node);
    }

    public MenuLabel(String string, Node node, MenuItem... mis) {
        super(string, node, mis);
    }

}

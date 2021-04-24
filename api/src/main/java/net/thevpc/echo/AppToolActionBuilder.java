/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author vpc
 */
public interface AppToolActionBuilder {

    AppToolActionBuilder id(String id);

    AppToolActionBuilder path(String... path);

    AppToolAction tool();

    AppToolComponent<AppToolAction> component();

    List<AppToolComponent<AppToolAction>> components();

    AppToolActionBuilder bind(ActionListener a);

    AppToolActionBuilder bind(Runnable a);

}

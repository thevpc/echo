/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

/**
 *
 * @author thevpc
 */
public interface AppPropertiesNodeItem extends AppPropertiesNode {

    Object getEvaluatedValue();

    Object[] getValues();

    Class getType();

    boolean isEditable();

    void setValue(Object aValue);
    
}

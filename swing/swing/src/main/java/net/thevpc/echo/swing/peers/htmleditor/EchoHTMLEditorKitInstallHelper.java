/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers.htmleditor;

import java.awt.event.ActionEvent;
import javax.swing.ActionMap;
import javax.swing.JEditorPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import net.thevpc.more.shef.MinWysiwygHTMLEditorKitInstallHelper;

/**
 *
 * @author vpc
 */
public class EchoHTMLEditorKitInstallHelper extends MinWysiwygHTMLEditorKitInstallHelper {

    @Override
    public void install(JEditorPane editor) {
        super.install(editor);
        ActionMap actionMap = editor.getActionMap();
        actionMap.put("font-strike-through", new StrikeAction());
    }

    public static class StrikeAction extends StyledEditorKit.StyledTextAction {

        /**
         * Constructs a new UnderlineAction.
         */
        public StrikeAction() {
            super("font-strike-through");
        }

        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attr = kit.getInputAttributes();
                boolean old = (StyleConstants.isStrikeThrough(attr)) ? false : true;
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setStrikeThrough(sas, old);
                setCharacterAttributes(editor, sas, false);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers.htmleditor;

import net.thevpc.more.shef.MinWysiwygHTMLEditorKitInstallHelper;
import net.thevpc.more.shef.WysiwygHTMLEditorKit;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author vpc
 */
public class EchoHTMLEditorKitInstallHelper extends MinWysiwygHTMLEditorKitInstallHelper {

    public static String getDocumentText2(Document doc) {
        return getDocumentText2(doc,new WysiwygHTMLEditorKit());
    }

    public static String getDocumentText2(Document doc, EditorKit k) {
        String txt;
        try {
            StringWriter out = new StringWriter();
            try {
                k.write(out, doc, 0, doc.getLength());
            } catch (BadLocationException e) {
                throw new IOException(e.getMessage());
            }
            txt = out.toString();
        } catch (IOException ioe) {
            txt = null;
        }
        return txt;
    }

    public static String getDocumentText(Document d) {
        try {
            return d.getText(0, d.getLength());
        } catch (Exception ex) {
            return null;
        }
    }

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
